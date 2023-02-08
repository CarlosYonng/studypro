package com.study.lock;

import java.util.concurrent.CountDownLatch;

public class TestHarness {


    /**
     * 同步工具类 闭锁  countDownLatch
     * 应用场景为多个线程在达到某一共同条件后同时执行，
     * 例中表示：当所有线程一开始都在等待startGate闭锁打开，在主线程中闭锁打开后，所有线程会同时执行task.run方法，
     *          这样可以计算并发线程同时处理问题的耗时时长
     * 也可以理解为：有n个人，一个裁判。这n个人同时跑，裁判开始计时，n个人都到终点了，裁判喊停，然后统计这n个人从开
     *          始跑到最后一个撞线用了多长时间
     * **/
    public long calTasksExecuteTime(int nThreads, final Runnable task) throws InterruptedException{
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);
        for (int i=0; i<nThreads; i++) {
            Thread t = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException ignored) {

                    }
                }
            };
            t.start();
        }
        long start = System.nanoTime();
        startGate.countDown();
        endGate.await();
        long end = System.nanoTime();
        return end-start;
    }
}
