package com.study.cache.example;

public interface Computable<A, V> {
    V computable(A arg) throws InterruptedException;
}
