package com.study.cache.example;


import com.study.exception.ExceptionTool;

import java.util.concurrent.*;

public class Memoizer<A, V> implements Computable<A, V> {

    /**
     * concurrenthashmap包并发安全，内部存入future任务（可能是计算中也可能是计算完的任务）可以避免重复计算，
     * 因为future可以通过get方法获取计算结果
     * A为入参，Future<V>为返回值(该返回值以一个任务参数表示)
     */
    private final ConcurrentHashMap<A, Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();

    private final Computable<A, V> c;

    public Memoizer(Computable<A, V> c) {
        this.c = c;
    }

    @Override
    public V computable(final A arg) throws InterruptedException {
        while (true) {
            /**
             * Future 接口是一个通用接口，表示从异步计算返回的值。它包含检查计算是否已完成或等待它，检索结果的方法。
             * 在前面的代码中，Future 的 get() 方法阻塞了调用线程，等待 Callable 完成计算，然后检索结果。
             * 该接口还包含取消 Callable 执行的方法。但是，一旦计算完成，就不能取消。
             *
             * **/
            Future<V> f = cache.get(arg);
            if (f == null) {
                /**
                 * 异步计算过程，并通过eval返回计算结果
                 * **/
                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws InterruptedException {
                        return c.computable(arg);
                    }
                };
                FutureTask<V> ft = new FutureTask<>(eval);
                //ConcurrenHashMap原子性操作 存入缓存中，防止多线程偶发重复存储
                f = cache.putIfAbsent(arg, ft);

                //缓存中没有存储过该计算任务，那么将该计算任务赋值给Future对象f，并执行该计算任务就可以通过f.get获取计算返回值
                if (f == null) {
                    f = ft;
                    ft.run();
                }
            }
            try {
                return f.get();
            } catch (CancellationException e) {
                //发现计算任务被取消，那么移除缓存中的任务数据，其他情况取消具体场景具体处理
                cache.remove(arg);
            } catch (ExecutionException e) {
                /**
                 * Callable表示的任务可以抛出受检查的或未受检查的异常，并且任何代码都可能抛出一个Error。无论代码抛出什么异常
                 * 都会被封装到一个ExecutionException中，并在Future中重新抛出，这将使get的调用代码变得复杂，
                 * 当future.get方法抛出ExecutionException时，可能是以下三种情况：Callable抛出的受检查异常，
                 * RuntimeException以及Error，所以要对每种情况单独处理，所以封装在launderThrowable中。
                 * **/
                throw ExceptionTool.launderThrowable(e.getCause());
            }
        }
    }


}
