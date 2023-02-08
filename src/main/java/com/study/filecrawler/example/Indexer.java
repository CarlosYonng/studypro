package com.study.filecrawler.example;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Indexer implements Runnable{

    public final BlockingQueue<File> queue;

    public Indexer(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                indexFile(queue.take());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void indexFile(File file) {

        //...
    }
}
