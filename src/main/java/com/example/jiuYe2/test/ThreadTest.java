package com.example.jiuYe2.test;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

// 继承Thread并重载run()方法。
class MyThread extends Thread {

    private final int tid;

    public MyThread(int tid) {
        this.tid = tid;
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(tid + ": " + i);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}

class Consumer implements Runnable {

    BlockingQueue<Integer> queue;
    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                int item = queue.take();
                System.out.println(Thread.currentThread().getName() + ": 取出" + item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Producer implements Runnable {

    // 同步队列可实现PV操作。
    BlockingQueue<Integer> queue;
    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                queue.put(i);
                System.out.println(Thread.currentThread().getName() + ": 放入" + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ThreadTest {

    public static void queueThread() {
        // 队列中可放5个元素。
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(5);
        new Thread(new Producer(queue), "Producer1").start();
        // Thread构造方法第二个参数可传进程名。
        new Thread(new Consumer(queue), "Consumer1").start();
        new Thread(new Consumer(queue), "Consumer2").start();

    }

    public static void runnableThread() {
        for (int i = 0; i < 5; i++) {
            // 内部类使用外部变量是持续使用，为了防止外部变量随生命周期结束被销毁，需要重新赋值给稳定变量。
            int finalI = i;
            // 实现Runnable的run()方法，new Thread()的参数为Runnable类，若class A implements Runnable，也可new A()。
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        try {
                            Thread.sleep(1000);
                            System.out.println(finalI + ": " + j);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

//            大部分参数可用lambda简化。
//            new Thread(() -> {
//                for (int j = 0; j < 5; j++) {
//                    try {
//                        Thread.sleep(1000);
//                        System.out.println(finalI + ": " + j);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
        }
    }

    private static final Object lock = new Object();

    public static void lockThread1() {
        // 相同lock的块会互斥执行。
        synchronized (lock) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println("lock1: " + i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void lockThread2() {
        synchronized (lock) {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                    System.out.println("lock2: " + i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    // 本地线程变量，每个线程维护自己的变量，互不干预。
    private static final ThreadLocal<Integer> users = new ThreadLocal<>();

    // 线程框架
    private static void threadExecutor() {
        // 单线程，提交的多个线程无法并发工作。
//        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // 线程池，提交的多个线程可以并发工作，参数指定线程数。
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Executor1: " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Executor2: " + i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // 任务执行完后关闭线程框架
        executorService.shutdown();
    }

    // 线程安全性变量，对数据进行原子操作。
    public static AtomicInteger counter = new AtomicInteger(0);

    public static void  atomicCount() {
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 5; j++) {
                        System.out.println("thread" + finalI + "操作，目前计数为：" + counter.incrementAndGet());
                    }
                }
            }).start();
        }
    }

    // 异步取值
    public static void futureResult() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Future直到等待的线程运行完毕并返回或失败后才执行其方法。
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(3000);
                return 233;
            }
        });
        executorService.shutdown();
        try {
            // 等待的线程完毕或失败后此句才执行，get(时间长度, 时间单位)可设置超时时间。
            System.out.println(future.get(1000, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//
//        futureResult();

//        atomicCount();

//        threadExecutor();

//        for (int i = 0; i < 5; i++) {
//            int finalI = i;
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                       users.set(finalI);
//                        System.out.println("users: " + finalI);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
//        }

//        queueThread();

//        for (int i = 0; i < 5; i++) {
//            lockThread1();
//            lockThread2();
//        }


//        runnableThread();

//        for (int i = 0; i < 5; i++) {
//            new MyThread(i).start();
//        }
//    }

}
