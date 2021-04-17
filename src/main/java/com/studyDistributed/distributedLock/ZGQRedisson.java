///**
// * Copyright (c) 2013-2019 Nikita Koksharov
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.redisson;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.UUID;
//import java.util.concurrent.ConcurrentMap;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicBoolean;
//import java.util.concurrent.atomic.AtomicLong;
//import java.util.concurrent.atomic.AtomicReference;
//import java.util.concurrent.locks.Condition;
//
//import org.redisson.api.RFuture;
//import org.redisson.api.RLock;
//import org.redisson.client.RedisException;
//import org.redisson.client.codec.LongCodec;
//import org.redisson.client.protocol.RedisCommand;
//import org.redisson.client.protocol.RedisCommand.ValueType;
//import org.redisson.client.protocol.RedisCommands;
//import org.redisson.client.protocol.RedisStrictCommand;
//import org.redisson.client.protocol.convertor.IntegerReplayConvertor;
//import org.redisson.command.CommandAsyncExecutor;
//import org.redisson.misc.RPromise;
//import org.redisson.misc.RedissonPromise;
//import org.redisson.pubsub.LockPubSub;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.netty.util.Timeout;
//import io.netty.util.TimerTask;
//import io.netty.util.concurrent.Future;
//import io.netty.util.concurrent.FutureListener;
//import io.netty.util.internal.PlatformDependent;
//
///**
// * Distributed implementation of {@link java.util.concurrent.locks.Lock}
// * Implements reentrant lock.<br>
// * Lock will be removed automatically if client disconnects.
// * <p>
// * Implements a <b>non-fair</b> locking so doesn't guarantees an acquire order.
// *
// * @author Nikita Koksharov
// *
// */
//public class ZGQRedissonLock extends RedissonExpirable implements RLock {
//
//    public static class ExpirationEntry {
//
//        private long threadId;
//        private Timeout timeout;
//
//        public ExpirationEntry(long threadId, Timeout timeout) {
//            super();
//            this.threadId = threadId;
//            this.timeout = timeout;
//        }
//
//        public long getThreadId() {
//            return threadId;
//        }
//
//        public Timeout getTimeout() {
//            return timeout;
//        }
//
//    }
//
//    private static final Logger log = LoggerFactory.getLogger(RedissonLock.class);
//
//    private static final ConcurrentMap<String, ExpirationEntry> expirationRenewalMap = PlatformDependent.newConcurrentHashMap();
//    protected long internalLockLeaseTime;
//
//    final UUID id;
//    final String entryName;
//
//    protected static final LockPubSub PUBSUB = new LockPubSub();
//
//    final CommandAsyncExecutor commandExecutor;
//
//    public RedissonLock(CommandAsyncExecutor commandExecutor, String name) {
//        super(commandExecutor, name);
//        this.commandExecutor = commandExecutor;
//        this.id = commandExecutor.getConnectionManager().getId();
//        this.internalLockLeaseTime = commandExecutor.getConnectionManager().getCfg().getLockWatchdogTimeout();//初始化锁的过期时间，看门狗 30秒
//        this.entryName = id + ":" + name;
//    }
//
//    protected String getEntryName() {
//        return entryName;
//    }
//
//    String getChannelName() {
//        return prefixName("redisson_lock__channel", getName());
//    }
//
//    protected String getLockName(long threadId) {
//        return id + ":" + threadId;
//    }
//
//    @Override
//    public void lock() {
//        try {
//            lockInterruptibly();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//    @Override
//    public void lock(long leaseTime, TimeUnit unit) {
//        try {
//            lockInterruptibly(leaseTime, unit);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//    }
//
//
//    @Override
//    public void lockInterruptibly() throws InterruptedException {
//        lockInterruptibly(-1, null);
//    }
//
//    @Override
//    public void lockInterruptibly(long leaseTime, TimeUnit unit) throws InterruptedException {
//        long threadId = Thread.currentThread().getId();//获取当前的线程id
//        Long ttl = tryAcquire(leaseTime, unit, threadId);//获取锁。若返回null表示获取到了
//        // lock acquired
//        if (ttl == null) {
//            return;
//        }
//
//        RFuture<RedissonLockEntry> future = subscribe(threadId);
//        commandExecutor.syncSubscription(future);
//
//        try {
//            while (true) {
//                ttl = tryAcquire(leaseTime, unit, threadId);
//                // lock acquired
//                if (ttl == null) {
//                    break;
//                }
//
//                // waiting for message
//                if (ttl >= 0) {
//                    getEntry(threadId).getLatch().tryAcquire(ttl, TimeUnit.MILLISECONDS);
//                } else {
//                    getEntry(threadId).getLatch().acquire();
//                }
//            }
//        } finally {
//            unsubscribe(future, threadId);
//        }
////        get(lockAsync(leaseTime, unit));
//    }
//
//    private Long tryAcquire(long leaseTime, TimeUnit unit, long threadId) {
//        return get(tryAcquireAsync(leaseTime, unit, threadId));
//    }
//
//    private RFuture<Boolean> tryAcquireOnceAsync(long leaseTime, TimeUnit unit, final long threadId) {
//        if (leaseTime != -1) {
//            return tryLockInnerAsync(leaseTime, unit, threadId, RedisCommands.EVAL_NULL_BOOLEAN);
//        }
//        RFuture<Boolean> ttlRemainingFuture = tryLockInnerAsync(commandExecutor.getConnectionManager().getCfg().getLockWatchdogTimeout(), TimeUnit.MILLISECONDS, threadId, RedisCommands.EVAL_NULL_BOOLEAN);
//        ttlRemainingFuture.addListener(new FutureListener<Boolean>() {
//            @Override
//            public void operationComplete(Future<Boolean> future) throws Exception {
//                if (!future.isSuccess()) {
//                    return;
//                }
//
//                Boolean ttlRemaining = future.getNow();
//                // lock acquired
//                if (ttlRemaining) {
//                    scheduleExpirationRenewal(threadId);
//                }
//            }
//        });
//        return ttlRemainingFuture;
//    }
//
//    private <T> RFuture<Long> tryAcquireAsync(long leaseTime, TimeUnit unit, final long threadId) {
//        /*-------ZGQ-----
//         * 锁的过期时间leaseTime等于使用的是lock(10, TimeUnit.SECONDS)方法的入参，leaseTime=10
//         * tryLockInnerAsync()方法就是去redis设置key
//         */
//        if (leaseTime != -1) {//当使用的是lock(10, TimeUnit.SECONDS)方法时，leaseTime=10 会进入if
//            return tryLockInnerAsync(leaseTime, unit, threadId, RedisCommands.EVAL_LONG);
//        }
//
//        //当使用lock()方法时没有指定锁的过期时间就不会进入if
//        /*-------ZGQ-----
//         * 锁的过期时间leaseTime=commandExecutor.getConnectionManager().getCfg().getLockWatchdogTimeout() 看门狗时间30秒
//         */
//        RFuture<Long> ttlRemainingFuture = tryLockInnerAsync(commandExecutor.getConnectionManager().getCfg().getLockWatchdogTimeout(), TimeUnit.MILLISECONDS, threadId, RedisCommands.EVAL_LONG);
//
//        ttlRemainingFuture.addListener(new FutureListener<Long>() {//上面的设置key方法tryLockInnerAsync()是异步方法，当设置key成功会回调operationComplete()方法
//            @Override
//            public void operationComplete(Future<Long> future) throws Exception {
//                if (!future.isSuccess()) {
//                    return;
//                }
//
//                Long ttlRemaining = future.getNow();
//                // lock acquired
//                if (ttlRemaining == null) {
//                    scheduleExpirationRenewal(threadId);//这个方法就是对锁进行续命
//                }
//            }
//        });
//        return ttlRemainingFuture;
//    }
//
//    @Override
//    public boolean tryLock() {
//        return get(tryLockAsync());
//    }
//
//    private void scheduleExpirationRenewal(final long threadId) {
//        if (expirationRenewalMap.containsKey(getEntryName())) {
//            return;
//        }
//
//        /**---ZGQ---
//         * 开启定时任务。每过 看门狗时间/3=10秒 进行定时检查，执行run()方法
//         */
//        Timeout task = commandExecutor.getConnectionManager().newTimeout(new TimerTask() {
//            @Override
//            public void run(Timeout timeout) throws Exception {
//
//                //renewExpirationAsync()方法就是对key重新设置过期时间
//                RFuture<Boolean> future = renewExpirationAsync(threadId);
//
//                future.addListener(new FutureListener<Boolean>() {
//                    @Override
//                    public void operationComplete(Future<Boolean> future) throws Exception {
//                        expirationRenewalMap.remove(getEntryName());
//                        if (!future.isSuccess()) {
//                            log.error("Can't update lock " + getName() + " expiration", future.cause());
//                            return;
//                        }
//
//                        if (future.getNow()) {
//                            // reschedule itself
//                            scheduleExpirationRenewal(threadId);//对key进行续命方法
//                        }
//                    }
//                });
//            }
//
//        /*------ZGQ---
//         * 定时任务多久执行一次？
//         *   每隔  看门狗时间/3=10秒  执行一次  也就是每过10秒执行一次，就是20的时候会进行续期，重新续期到30秒
//         */
//        }, internalLockLeaseTime / 3, TimeUnit.MILLISECONDS);
//
//        if (expirationRenewalMap.putIfAbsent(getEntryName(), new ExpirationEntry(threadId, task)) != null) {
//            task.cancel();
//        }
//    }
//
//    protected RFuture<Boolean> renewExpirationAsync(long threadId) {
//        return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, RedisCommands.EVAL_BOOLEAN,
//                "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " + //判断key(KEYS[1])和value(ARGV[2])还存不存在，若等于1表示存在
//                        "redis.call('pexpire', KEYS[1], ARGV[1]); " + //若还存在对key重新设置超时时间，30秒
//                        "return 1; " +
//                        "end; " +
//                        "return 0;",
//                Collections.<Object>singletonList(getName()),
//                internalLockLeaseTime, getLockName(threadId));
//    }
//
//    void cancelExpirationRenewal(Long threadId) {
//        ExpirationEntry task = expirationRenewalMap.get(getEntryName());
//        if (task != null && (threadId == null || task.getThreadId() == threadId)) {
//            expirationRenewalMap.remove(getEntryName());
//            task.getTimeout().cancel();
//        }
//    }
//
//    <T> RFuture<T> tryLockInnerAsync(long leaseTime, TimeUnit unit, long threadId, RedisStrictCommand<T> command) {
//        internalLockLeaseTime = unit.toMillis(leaseTime);
//
//        return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, command,//这段lua脚本是进行加锁（向redis设置key）
//                "if (redis.call('exists', KEYS[1]) == 0) then " + //这一句就是判断redis有没有加锁的那个key
//                        "redis.call('hset', KEYS[1], ARGV[2], 1); " + //若key不存在，则调用hset进行设置key。key对应的value是ARGV[2]，这个value跟线程id有关，用来标识这个key是谁设置的
//                        "redis.call('pexpire', KEYS[1], ARGV[1]); " + //对key设置超时时间，超时时间为ARGV[1]=internalLockLeaseTime看门狗 30秒
//                        "return nil; " +
//                        "end; " +
//                        "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
//                        "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
//                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
//                        "return nil; " +
//                        "end; " +
//                        "return redis.call('pttl', KEYS[1]);",
//                Collections.<Object>singletonList(getName()), internalLockLeaseTime, getLockName(threadId));
//    }
//
//    private void acquireFailed(long threadId) {
//        get(acquireFailedAsync(threadId));
//    }
//
//    protected RFuture<Void> acquireFailedAsync(long threadId) {
//        return RedissonPromise.newSucceededFuture(null);
//    }
//
//    /**
//     * tryLock()方法一般用于特定满足需求的场合，但不建议作为一般需求的分布式锁，一般分布式锁建议用void lock(long leaseTime, TimeUnit unit)。
//     * 因为从性能上考虑，在高并发情况下后者效率是前者的好几倍
//     */
//    @Override
//    public boolean tryLock(long waitTime, long leaseTime, TimeUnit unit) throws InterruptedException {
//        long time = unit.toMillis(waitTime);
//        long current = System.currentTimeMillis();
//        final long threadId = Thread.currentThread().getId();
//        Long ttl = tryAcquire(leaseTime, unit, threadId);
//        // lock acquired
//        //获取锁同时获取成功的情况下，和lock(...)方法是一样的 直接返回True，没有获取到锁再往下走
//        if (ttl == null) {
//            return true;
//        }
//
//        //如果超过了尝试获取锁的等待时间,当然返回false 了。
//        time -= (System.currentTimeMillis() - current);
//        if (time <= 0) {
//            acquireFailed(threadId);
//            return false;
//        }
//
//        current = System.currentTimeMillis();
//        //订阅监听redis消息，并且创建RedissonLockEntry，其中RedissonLockEntry中比较关键的是一个 Semaphore属性对象,
//        //用来控制本地的锁请求的信号量同步，返回的是netty框架的Future实现。
//        final RFuture<RedissonLockEntry> subscribeFuture = subscribe(threadId);
//
//        //  阻塞等待subscribe的future的结果对象，如果subscribe方法调用超过了time，说明已经超过了客户端设置的最大wait time，
//        //  则直接返回false，取消订阅，不再继续申请锁了。
//        //  只有await返回true，才进入循环尝试获取锁
//        if (!await(subscribeFuture, time, TimeUnit.MILLISECONDS)) {
//            if (!subscribeFuture.cancel(false)) {
//                subscribeFuture.addListener(new FutureListener<RedissonLockEntry>() {
//                    @Override
//                    public void operationComplete(Future<RedissonLockEntry> future) throws Exception {
//                        if (subscribeFuture.isSuccess()) {
//                            unsubscribe(subscribeFuture, threadId);
//                        }
//                    }
//                });
//            }
//            acquireFailed(threadId);
//            return false;
//        }
//
//        try {
//            time -= (System.currentTimeMillis() - current);
//            if (time <= 0) {
//                acquireFailed(threadId);
//                return false;
//            }
//
//            //4、如果没有超过尝试获取锁的等待时间，那么通过While一直获取锁。最终只会有两种结果
//            //1)、在等待时间内获取锁成功 返回true。2）等待时间结束了还没有获取到锁那么返回false。
//            while (true) {
//                long currentTime = System.currentTimeMillis();
//                ttl = tryAcquire(leaseTime, unit, threadId);
//                // lock acquired
//                // 获取锁成功
//                if (ttl == null) {
//                    return true;
//                }
//
//                // 获取锁失败
//                time -= (System.currentTimeMillis() - currentTime);
//                if (time <= 0) {
//                    acquireFailed(threadId);
//                    return false;
//                }
//
//                // waiting for message
//                currentTime = System.currentTimeMillis();
//                if (ttl >= 0 && ttl < time) {
//                    getEntry(threadId).getLatch().tryAcquire(ttl, TimeUnit.MILLISECONDS);
//                } else {
//                    getEntry(threadId).getLatch().tryAcquire(time, TimeUnit.MILLISECONDS);
//                }
//
//                time -= (System.currentTimeMillis() - currentTime);
//                if (time <= 0) {
//                    acquireFailed(threadId);
//                    return false;
//                }
//            }
//        } finally {
//            unsubscribe(subscribeFuture, threadId);
//        }
////        return get(tryLockAsync(waitTime, leaseTime, unit));
//    }
//
//    protected RedissonLockEntry getEntry(long threadId) {
//        return PUBSUB.getEntry(getEntryName());
//    }
//
//    protected RFuture<RedissonLockEntry> subscribe(long threadId) {
//        return PUBSUB.subscribe(getEntryName(), getChannelName(), commandExecutor.getConnectionManager().getSubscribeService());
//    }
//
//    protected void unsubscribe(RFuture<RedissonLockEntry> future, long threadId) {
//        PUBSUB.unsubscribe(future.getNow(), getEntryName(), getChannelName(), commandExecutor.getConnectionManager().getSubscribeService());
//    }
//
//    @Override
//    public boolean tryLock(long waitTime, TimeUnit unit) throws InterruptedException {
//        return tryLock(waitTime, -1, unit);
//    }
//
//    @Override
//    public void unlock() {
//        try {
//            get(unlockAsync(Thread.currentThread().getId()));
//        } catch (RedisException e) {
//            if (e.getCause() instanceof IllegalMonitorStateException) {
//                throw (IllegalMonitorStateException)e.getCause();
//            } else {
//                throw e;
//            }
//        }
//
////        Future<Void> future = unlockAsync();
////        future.awaitUninterruptibly();
////        if (future.isSuccess()) {
////            return;
////        }
////        if (future.cause() instanceof IllegalMonitorStateException) {
////            throw (IllegalMonitorStateException)future.cause();
////        }
////        throw commandExecutor.convertException(future);
//    }
//
//    @Override
//    public Condition newCondition() {
//        // TODO implement
//        throw new UnsupportedOperationException();
//    }
//
//    @Override
//    public boolean forceUnlock() {
//        return get(forceUnlockAsync());
//    }
//
//    @Override
//    public RFuture<Boolean> forceUnlockAsync() {
//        cancelExpirationRenewal(null);
//        return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, RedisCommands.EVAL_BOOLEAN,
//                "if (redis.call('del', KEYS[1]) == 1) then "
//                        + "redis.call('publish', KEYS[2], ARGV[1]); "
//                        + "return 1 "
//                        + "else "
//                        + "return 0 "
//                        + "end",
//                Arrays.<Object>asList(getName(), getChannelName()), LockPubSub.unlockMessage);
//    }
//
//    @Override
//    public boolean isLocked() {
//        return isExists();
//    }
//
//    @Override
//    public RFuture<Boolean> isExistsAsync() {
//        return commandExecutor.writeAsync(getName(), codec, RedisCommands.EXISTS, getName());
//    }
//
//    @Override
//    public boolean isHeldByCurrentThread() {
//        return isHeldByThread(Thread.currentThread().getId());
//    }
//
//    @Override
//    public boolean isHeldByThread(long threadId) {
//        final RFuture<Boolean> future = commandExecutor.writeAsync(getName(), LongCodec.INSTANCE, RedisCommands.HEXISTS, getName(), getLockName(threadId));
//        return get(future);
//    }
//
//    private static final RedisCommand<Integer> HGET = new RedisCommand<Integer>("HGET", ValueType.MAP_VALUE, new IntegerReplayConvertor(0));
//
//    public RFuture<Integer> getHoldCountAsync() {
//        return commandExecutor.writeAsync(getName(), LongCodec.INSTANCE, HGET, getName(), getLockName(Thread.currentThread().getId()));
//    }
//
//    @Override
//    public int getHoldCount() {
//        return get(getHoldCountAsync());
//    }
//
//    @Override
//    public RFuture<Boolean> deleteAsync() {
//        return forceUnlockAsync();
//    }
//
//    @Override
//    public RFuture<Void> unlockAsync() {
//        long threadId = Thread.currentThread().getId();
//        return unlockAsync(threadId);
//    }
//
//    protected RFuture<Boolean> unlockInnerAsync(long threadId) {
//        return commandExecutor.evalWriteAsync(getName(), LongCodec.INSTANCE, RedisCommands.EVAL_BOOLEAN,
//                "if (redis.call('exists', KEYS[1]) == 0) then " +
//                        "redis.call('publish', KEYS[2], ARGV[1]); " +
//                        "return 1; " +
//                        "end;" +
//                        "if (redis.call('hexists', KEYS[1], ARGV[3]) == 0) then " +
//                        "return nil;" +
//                        "end; " +
//                        "local counter = redis.call('hincrby', KEYS[1], ARGV[3], -1); " +
//                        "if (counter > 0) then " +
//                        "redis.call('pexpire', KEYS[1], ARGV[2]); " +
//                        "return 0; " +
//                        "else " +
//                        "redis.call('del', KEYS[1]); " +
//                        "redis.call('publish', KEYS[2], ARGV[1]); " +
//                        "return 1; "+
//                        "end; " +
//                        "return nil;",
//                Arrays.<Object>asList(getName(), getChannelName()), LockPubSub.unlockMessage, internalLockLeaseTime, getLockName(threadId));
//
//    }
//
//    @Override
//    public RFuture<Void> unlockAsync(final long threadId) {
//        final RPromise<Void> result = new RedissonPromise<Void>();
//        RFuture<Boolean> future = unlockInnerAsync(threadId);
//
//        future.addListener(new FutureListener<Boolean>() {
//            @Override
//            public void operationComplete(Future<Boolean> future) throws Exception {
//                if (!future.isSuccess()) {
//                    cancelExpirationRenewal(threadId);
//                    result.tryFailure(future.cause());
//                    return;
//                }
//
//                Boolean opStatus = future.getNow();
//                if (opStatus == null) {
//                    IllegalMonitorStateException cause = new IllegalMonitorStateException("attempt to unlock lock, not locked by current thread by node id: "
//                            + id + " thread-id: " + threadId);
//                    result.tryFailure(cause);
//                    return;
//                }
//                if (opStatus) {
//                    cancelExpirationRenewal(null);
//                }
//                result.trySuccess(null);
//            }
//        });
//
//        return result;
//    }
//
//    @Override
//    public RFuture<Void> lockAsync() {
//        return lockAsync(-1, null);
//    }
//
//    @Override
//    public RFuture<Void> lockAsync(long leaseTime, TimeUnit unit) {
//        final long currentThreadId = Thread.currentThread().getId();
//        return lockAsync(leaseTime, unit, currentThreadId);
//    }
//
//    @Override
//    public RFuture<Void> lockAsync(long currentThreadId) {
//        return lockAsync(-1, null, currentThreadId);
//    }
//
//    @Override
//    public RFuture<Void> lockAsync(final long leaseTime, final TimeUnit unit, final long currentThreadId) {
//        final RPromise<Void> result = new RedissonPromise<Void>();
//        RFuture<Long> ttlFuture = tryAcquireAsync(leaseTime, unit, currentThreadId);
//        ttlFuture.addListener(new FutureListener<Long>() {
//            @Override
//            public void operationComplete(Future<Long> future) throws Exception {
//                if (!future.isSuccess()) {
//                    result.tryFailure(future.cause());
//                    return;
//                }
//
//                Long ttl = future.getNow();
//
//                // lock acquired
//                if (ttl == null) {
//                    if (!result.trySuccess(null)) {
//                        unlockAsync(currentThreadId);
//                    }
//                    return;
//                }
//
//                final RFuture<RedissonLockEntry> subscribeFuture = subscribe(currentThreadId);
//                subscribeFuture.addListener(new FutureListener<RedissonLockEntry>() {
//                    @Override
//                    public void operationComplete(Future<RedissonLockEntry> future) throws Exception {
//                        if (!future.isSuccess()) {
//                            result.tryFailure(future.cause());
//                            return;
//                        }
//
//                        lockAsync(leaseTime, unit, subscribeFuture, result, currentThreadId);
//                    }
//
//                });
//            }
//        });
//
//        return result;
//    }
//
//    private void lockAsync(final long leaseTime, final TimeUnit unit,
//                           final RFuture<RedissonLockEntry> subscribeFuture, final RPromise<Void> result, final long currentThreadId) {
//        RFuture<Long> ttlFuture = tryAcquireAsync(leaseTime, unit, currentThreadId);
//        ttlFuture.addListener(new FutureListener<Long>() {
//            @Override
//            public void operationComplete(Future<Long> future) throws Exception {
//                if (!future.isSuccess()) {
//                    unsubscribe(subscribeFuture, currentThreadId);
//                    result.tryFailure(future.cause());
//                    return;
//                }
//
//                Long ttl = future.getNow();
//                // lock acquired
//                if (ttl == null) {
//                    unsubscribe(subscribeFuture, currentThreadId);
//                    if (!result.trySuccess(null)) {
//                        unlockAsync(currentThreadId);
//                    }
//                    return;
//                }
//
//                final RedissonLockEntry entry = getEntry(currentThreadId);
//                if (entry.getLatch().tryAcquire()) {
//                    lockAsync(leaseTime, unit, subscribeFuture, result, currentThreadId);
//                } else {
//                    // waiting for message
//                    final AtomicReference<Timeout> futureRef = new AtomicReference<Timeout>();
//                    final Runnable listener = new Runnable() {
//                        @Override
//                        public void run() {
//                            if (futureRef.get() != null) {
//                                futureRef.get().cancel();
//                            }
//                            lockAsync(leaseTime, unit, subscribeFuture, result, currentThreadId);
//                        }
//                    };
//
//                    entry.addListener(listener);
//
//                    if (ttl >= 0) {
//                        Timeout scheduledFuture = commandExecutor.getConnectionManager().newTimeout(new TimerTask() {
//                            @Override
//                            public void run(Timeout timeout) throws Exception {
//                                if (entry.removeListener(listener)) {
//                                    lockAsync(leaseTime, unit, subscribeFuture, result, currentThreadId);
//                                }
//                            }
//                        }, ttl, TimeUnit.MILLISECONDS);
//                        futureRef.set(scheduledFuture);
//                    }
//                }
//            }
//        });
//    }
//
//    @Override
//    public RFuture<Boolean> tryLockAsync() {
//        return tryLockAsync(Thread.currentThread().getId());
//    }
//
//    @Override
//    public RFuture<Boolean> tryLockAsync(long threadId) {
//        return tryAcquireOnceAsync(-1, null, threadId);
//    }
//
//    @Override
//    public RFuture<Boolean> tryLockAsync(long waitTime, TimeUnit unit) {
//        return tryLockAsync(waitTime, -1, unit);
//    }
//
//    @Override
//    public RFuture<Boolean> tryLockAsync(long waitTime, long leaseTime, TimeUnit unit) {
//        long currentThreadId = Thread.currentThread().getId();
//        return tryLockAsync(waitTime, leaseTime, unit, currentThreadId);
//    }
//
//    @Override
//    public RFuture<Boolean> tryLockAsync(final long waitTime, final long leaseTime, final TimeUnit unit,
//                                         final long currentThreadId) {
//        final RPromise<Boolean> result = new RedissonPromise<Boolean>();
//
//        final AtomicLong time = new AtomicLong(unit.toMillis(waitTime));
//        final long currentTime = System.currentTimeMillis();
//        RFuture<Long> ttlFuture = tryAcquireAsync(leaseTime, unit, currentThreadId);
//        ttlFuture.addListener(new FutureListener<Long>() {
//            @Override
//            public void operationComplete(Future<Long> future) throws Exception {
//                if (!future.isSuccess()) {
//                    result.tryFailure(future.cause());
//                    return;
//                }
//
//                Long ttl = future.getNow();
//
//                // lock acquired
//                if (ttl == null) {
//                    if (!result.trySuccess(true)) {
//                        unlockAsync(currentThreadId);
//                    }
//                    return;
//                }
//
//                long elapsed = System.currentTimeMillis() - currentTime;
//                time.addAndGet(-elapsed);
//
//                if (time.get() <= 0) {
//                    trySuccessFalse(currentThreadId, result);
//                    return;
//                }
//
//                final long current = System.currentTimeMillis();
//                final AtomicReference<Timeout> futureRef = new AtomicReference<Timeout>();
//                final RFuture<RedissonLockEntry> subscribeFuture = subscribe(currentThreadId);
//                subscribeFuture.addListener(new FutureListener<RedissonLockEntry>() {
//                    @Override
//                    public void operationComplete(Future<RedissonLockEntry> future) throws Exception {
//                        if (!future.isSuccess()) {
//                            result.tryFailure(future.cause());
//                            return;
//                        }
//
//                        if (futureRef.get() != null) {
//                            futureRef.get().cancel();
//                        }
//
//                        long elapsed = System.currentTimeMillis() - current;
//                        time.addAndGet(-elapsed);
//
//                        tryLockAsync(time, leaseTime, unit, subscribeFuture, result, currentThreadId);
//                    }
//                });
//                if (!subscribeFuture.isDone()) {
//                    Timeout scheduledFuture = commandExecutor.getConnectionManager().newTimeout(new TimerTask() {
//                        @Override
//                        public void run(Timeout timeout) throws Exception {
//                            if (!subscribeFuture.isDone()) {
//                                subscribeFuture.cancel(false);
//                                trySuccessFalse(currentThreadId, result);
//                            }
//                        }
//                    }, time.get(), TimeUnit.MILLISECONDS);
//                    futureRef.set(scheduledFuture);
//                }
//            }
//
//        });
//
//
//        return result;
//    }
//
//    private void trySuccessFalse(final long currentThreadId, final RPromise<Boolean> result) {
//        acquireFailedAsync(currentThreadId).addListener(new FutureListener<Void>() {
//            @Override
//            public void operationComplete(Future<Void> future) throws Exception {
//                if (future.isSuccess()) {
//                    result.trySuccess(false);
//                } else {
//                    result.tryFailure(future.cause());
//                }
//            }
//        });
//    }
//
//    private void tryLockAsync(final AtomicLong time, final long leaseTime, final TimeUnit unit,
//                              final RFuture<RedissonLockEntry> subscribeFuture, final RPromise<Boolean> result, final long currentThreadId) {
//        if (result.isDone()) {
//            unsubscribe(subscribeFuture, currentThreadId);
//            return;
//        }
//
//        if (time.get() <= 0) {
//            unsubscribe(subscribeFuture, currentThreadId);
//            trySuccessFalse(currentThreadId, result);
//            return;
//        }
//
//        final long current = System.currentTimeMillis();
//        RFuture<Long> ttlFuture = tryAcquireAsync(leaseTime, unit, currentThreadId);
//        ttlFuture.addListener(new FutureListener<Long>() {
//            @Override
//            public void operationComplete(Future<Long> future) throws Exception {
//                if (!future.isSuccess()) {
//                    unsubscribe(subscribeFuture, currentThreadId);
//                    result.tryFailure(future.cause());
//                    return;
//                }
//
//                Long ttl = future.getNow();
//                // lock acquired
//                if (ttl == null) {
//                    unsubscribe(subscribeFuture, currentThreadId);
//                    if (!result.trySuccess(true)) {
//                        unlockAsync(currentThreadId);
//                    }
//                    return;
//                }
//
//                long elapsed = System.currentTimeMillis() - current;
//                time.addAndGet(-elapsed);
//
//                if (time.get() <= 0) {
//                    unsubscribe(subscribeFuture, currentThreadId);
//                    trySuccessFalse(currentThreadId, result);
//                    return;
//                }
//
//                // waiting for message
//                final long current = System.currentTimeMillis();
//                final RedissonLockEntry entry = getEntry(currentThreadId);
//                if (entry.getLatch().tryAcquire()) {
//                    tryLockAsync(time, leaseTime, unit, subscribeFuture, result, currentThreadId);
//                } else {
//                    final AtomicBoolean executed = new AtomicBoolean();
//                    final AtomicReference<Timeout> futureRef = new AtomicReference<Timeout>();
//                    final Runnable listener = new Runnable() {
//                        @Override
//                        public void run() {
//                            executed.set(true);
//                            if (futureRef.get() != null) {
//                                futureRef.get().cancel();
//                            }
//
//                            long elapsed = System.currentTimeMillis() - current;
//                            time.addAndGet(-elapsed);
//
//                            tryLockAsync(time, leaseTime, unit, subscribeFuture, result, currentThreadId);
//                        }
//                    };
//                    entry.addListener(listener);
//
//                    long t = time.get();
//                    if (ttl >= 0 && ttl < time.get()) {
//                        t = ttl;
//                    }
//                    if (!executed.get()) {
//                        Timeout scheduledFuture = commandExecutor.getConnectionManager().newTimeout(new TimerTask() {
//                            @Override
//                            public void run(Timeout timeout) throws Exception {
//                                if (entry.removeListener(listener)) {
//                                    long elapsed = System.currentTimeMillis() - current;
//                                    time.addAndGet(-elapsed);
//
//                                    tryLockAsync(time, leaseTime, unit, subscribeFuture, result, currentThreadId);
//                                }
//                            }
//                        }, t, TimeUnit.MILLISECONDS);
//                        futureRef.set(scheduledFuture);
//                    }
//                }
//            }
//        });
//    }
//
//
//}
//;
