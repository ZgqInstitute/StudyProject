package com.studyThread;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZGQReentrantLock implements Lock, java.io.Serializable {
    private static final long serialVersionUID = 7373984872572414699L;
    private final ZGQReentrantLock.Sync sync;

    abstract static class Sync extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = -5179523762034025860L;

        abstract void lock();

        /**---ZGQ---
         * @param acquires 当线程没有获取到锁从ReentrantLock的acquire()方法进入到这，acquires = 1
         */
        final boolean nonfairTryAcquire(int acquires) {
            final Thread current = Thread.currentThread();//ZGQ  得到当前未获取到锁的线程
            int c = getState();//ZGQ   获取AQS的state，由于进入到这AQS的state=1
            if (c == 0) {//ZGQ  当未获取到锁的线程执行到这时，获取到锁的线程刚好释放了锁将state置为0，所以未获取到锁的线程就能进入if
                if (compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);//ZGQ 刚开始未获取到锁的线程现在获取到了锁，并设置当前是自己占有了锁
                    return true;
                }
            }
            /**---ZGQ---
             * current：是当前未获取到锁的线程；
             * getExclusiveOwnerThread()：是获取当前占有锁的线程
             */
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0) // overflow
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }

        /**---ZGQ---
         * 之前占有锁的线程开始解锁
         */
        protected final boolean tryRelease(int releases) {
            /**---ZGQ---
             * getState() = 1
             * releases = 1
             * 所以 c = 0
             */
            int c = getState() - releases;
            if (Thread.currentThread() != getExclusiveOwnerThread())
                throw new IllegalMonitorStateException();
            boolean free = false;
            if (c == 0) {
                free = true;
                //ZGQ  设置当前锁的占有线程为null
                setExclusiveOwnerThread(null);
            }
            //ZGQ 将状态改为0
            setState(c);
            return free;
        }

        protected final boolean isHeldExclusively() {
            return getExclusiveOwnerThread() == Thread.currentThread();
        }

        final ConditionObject newCondition() {
            return new ConditionObject();
        }

        final Thread getOwner() {
            return getState() == 0 ? null : getExclusiveOwnerThread();
        }

        final int getHoldCount() {
            return isHeldExclusively() ? getState() : 0;
        }

        final boolean isLocked() {
            return getState() != 0;
        }

        private void readObject(java.io.ObjectInputStream s)
                throws java.io.IOException, ClassNotFoundException {
            s.defaultReadObject();
            setState(0); // reset to unlocked state
        }
    }

    static final class NonfairSync extends ZGQReentrantLock.Sync {
        private static final long serialVersionUID = 7316153563782823691L;

        final void lock() {
            /**---ZGQ---
             * 当state=0表示锁未被占有时才可以进入if
             *    expect：期望是0（0表示当前锁没有被占用）；
             *    update：若state = 0 则将state置为1
             */
            if (compareAndSetState(0, 1))
                //ZGQ  设置现在是哪个线程占有锁
                setExclusiveOwnerThread(Thread.currentThread());
            else
                //当state=1表示锁已经被占有，则进入else。会调用AQS的acquire()方法
                acquire(1);
        }

        //这个方法就是实现父类AQS的tryAcquire()方法
        protected final boolean tryAcquire(int acquires) {
            return nonfairTryAcquire(acquires);
        }
    }

    static final class FairSync extends ZGQReentrantLock.Sync {
        private static final long serialVersionUID = -3000897897090466540L;

        final void lock() {
            acquire(1);
        }

        protected final boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if (!hasQueuedPredecessors() &&
                        compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            }
            else if (current == getExclusiveOwnerThread()) {
                int nextc = c + acquires;
                if (nextc < 0)
                    throw new Error("Maximum lock count exceeded");
                setState(nextc);
                return true;
            }
            return false;
        }
    }

    public ZGQReentrantLock() {
        sync = new ZGQReentrantLock.NonfairSync();
    }

    public ZGQReentrantLock(boolean fair) {
        sync = fair ? new ZGQReentrantLock.FairSync() : new ZGQReentrantLock.NonfairSync();
    }

    public void lock() {
        sync.lock();
    }

    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    public boolean tryLock() {
        return sync.nonfairTryAcquire(1);
    }

    public boolean tryLock(long timeout, TimeUnit unit)
            throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(timeout));
    }

    public void unlock() {
        //ZGQ  调用lock.unlock()方法，底层调用的是sync.release()方法
        sync.release(1);
    }

    public Condition newCondition() {
        return sync.newCondition();
    }

    public int getHoldCount() {
        return sync.getHoldCount();
    }

    public boolean isHeldByCurrentThread() {
        return sync.isHeldExclusively();
    }

    public boolean isLocked() {
        return sync.isLocked();
    }

    public final boolean isFair() {
        return sync instanceof ZGQReentrantLock.FairSync;
    }

    protected Thread getOwner() {
        return sync.getOwner();
    }

    public final boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }

    public final boolean hasQueuedThread(Thread thread) {
        return sync.isQueued(thread);
    }

    public final int getQueueLength() {
        return sync.getQueueLength();
    }

    protected Collection<Thread> getQueuedThreads() {
        return sync.getQueuedThreads();
    }

    public boolean hasWaiters(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.hasWaiters((AbstractQueuedSynchronizer.ConditionObject)condition);
    }

    public int getWaitQueueLength(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.getWaitQueueLength((AbstractQueuedSynchronizer.ConditionObject)condition);
    }

    protected Collection<Thread> getWaitingThreads(Condition condition) {
        if (condition == null)
            throw new NullPointerException();
        if (!(condition instanceof AbstractQueuedSynchronizer.ConditionObject))
            throw new IllegalArgumentException("not owner");
        return sync.getWaitingThreads((AbstractQueuedSynchronizer.ConditionObject)condition);
    }

    public String toString() {
        Thread o = sync.getOwner();
        return super.toString() + ((o == null) ?
                "[Unlocked]" :
                "[Locked by thread " + o.getName() + "]");
    }
}
