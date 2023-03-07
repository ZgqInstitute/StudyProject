package com.studyThread.studyMutiThread.studyCompletableFuture;

import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureDemo {

    /**
     * 创建CompletableFuture
     * 测试runAsync(Runnable runnable)方法
     */
    @Test
    public void runAsync01__test() throws ExecutionException, InterruptedException {
        //<Void>表示无返回值
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            //ForkJoinPool.commonPool-worker-1：若没有指定线程池，那么使用默认的ForkJoinPool线程池
            System.out.println(Thread.currentThread().getName());

            // 业务执行时间
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //null：由于runAsync()方法没有返回值，所以调用get得到null
        System.out.println(completableFuture.get());
    }

    /**
     * 创建CompletableFuture
     * 测试runAsync(Runnable runnable,Executor executor)方法
     */
    @Test
    public void runAsync02__test() throws ExecutionException, InterruptedException {
        // 自定义线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        //<Void>表示无返回值
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            //pool-1-thread-1：若指定了线程池，那么使用指定的线程池
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, threadPool);

        //null：由于runAsync()方法没有返回值，所以调用get得到null
        System.out.println(completableFuture.get());
    }

    /**
     * 创建CompletableFuture
     * 测试supplyAsync(Supplier<U> supplier)方法
     */
    @Test
    public void supplyAsync01__test() throws ExecutionException, InterruptedException {
        String para = "入参";

        //<String>表示返回值类型
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //ForkJoinPool.commonPool-worker-1：若没有指定线程池，那么使用默认的ForkJoinPool线程池
            System.out.println(Thread.currentThread().getName());
            String res = "出参" + " , " + para;

            // 业务执行时间
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return res;
        });

        //返回：出参 , 入参
        System.out.println(completableFuture.get());
    }

    /**
     * 创建CompletableFuture
     * 测试supplyAsync(Supplier<U> supplier, Executor executor)方法
     */
    @Test
    public void supplyAsync02__test() throws ExecutionException, InterruptedException {
        String para = "入参";
        // 自定义线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        //<String>表示返回值类型
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            //pool-1-thread-1：若指定了线程池，那么使用指定的线程池
            System.out.println(Thread.currentThread().getName());
            String res = "出参" + " , " + para;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return res;
        }, threadPool);

        //返回：出参 , 入参
        System.out.println(completableFuture.get());
    }

    /**
     * 创建CompletableFuture
     * 测试supplyAsync(Supplier<U> supplier)方法
     */
    @Test
    public void whenComplete_test() {
        ExecutorService threadPool = Executors.newFixedThreadPool(6);

        try {
            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName());
                String res = "返回值";
                return res;
            }, threadPool)
                    /**
                     * 有三个处理流程的方法：
                     * （1）thenRun(Runnable runnable)无入参，无返回值。使用场景：下一个任务不需要上一个任务的结果
                     * （2）thenAccept(Consumer consumer)有入参，无返回值。使用场景：入参就是上一个任务的结果，但是使用thenAccept没有返回值，下一个任务不能使用当前任务的结果
                     * （3）thenApply(Function function)有入参，有返回值。使用场景：当前任务可以使用上一个任务的结果，也能将结果传给下一个任务
                     *         若任务a和任务b存在依赖关系，使用thenApply，当任务a执行出错将不能执行任务b
                     *         handle(BiFunction<? super T, Throwable, ? extends U> fn) 三个参数，2个入参，一个出参
                     *         handle方法的正常执行与thenApply相同，异常与thenApply不同，当任务a出现异常也可以继续往下面执行
                     */
                    .thenApply(re -> {//re是上一步的结果
                        System.out.println("处理任务a");
                        return re + "任务a";
                    })
                    .thenApply(re -> {//re是上一步的结果
                        System.out.println("处理任务b");
                        return re + "任务b";
                    })
                    .whenComplete((v, e) -> {//v是上面的返回值res   e是上面的操作产生的异常
                        if (e == null) {//若e为null，说明没有异常，上面执行成功
                            System.out.println(Thread.currentThread().getName() + "上面的异步调用成功，返回值 = " + v);
                        }
                    }).exceptionally(e -> {
                        return "出异常了";
                    });

            /**
             * CompletableFuture获得结果API
             * (1) T get()
             * (2) T get(long timeout, TimeUnit unit)
             * (3) T join()  跟get类似，但是join没有抛异常
             * (4) T getNow(T valueIfAbsent)  当调用getNow()时若结果还没有返回，就返回默认的值valueIfAbsent
             * (5) boolean complete(T value)  用法与getNow类似，当返回complete方法参数的value时，返回值为true，否则为false
             */
            //返回：出参 , 入参
            System.out.println(Thread.currentThread().getName() + "最后的结果：" + completableFuture.get());
//            System.out.println(Thread.currentThread().getName() + "最后的结果：" + completableFuture.join());
//            System.out.println(Thread.currentThread().getName() + "最后的结果：" + completableFuture.getNow("默认值"));
//            System.out.println(Thread.currentThread().getName() + "最后的结果：" + completableFuture.complete("默认值"));


            //说明： 若主线程先执行完成，而且使用的是默认的线程池ForkJoinPool，那么上面线程池中的线程会变为守护线程，会退出，也得不到结果

        } catch (Exception e) {

        } finally {
            threadPool.shutdown();
        }

    }
}
