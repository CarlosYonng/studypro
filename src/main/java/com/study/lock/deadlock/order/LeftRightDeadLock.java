package com.study.lock.deadlock.order;

/**
 * 简单的锁顺序死锁
 * **/
public class LeftRightDeadLock {

    private final Object left = new Object();
    private final Object right = new Object();

    public void leftRight() {
        synchronized (left) {
            synchronized (right) {
                //doSomething
                doSomething();
            }
        }
    }

    public void rightLeft() {
        synchronized (right) {
            synchronized (left) {
                //doSomething
                doSomething();
            }
        }
    }
    public void doSomething() {
        System.out.println("doSomething---输出内容！！！");
    }

    public static void main(String[] args) {
        LeftRightDeadLock leftRightDeadLock = new LeftRightDeadLock();
        int T = 100;
        while (T-->0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    leftRightDeadLock.leftRight();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    leftRightDeadLock.rightLeft();
                }
            }).start();
            System.out.println("第"+T+"次输出");
        }

    }
}
