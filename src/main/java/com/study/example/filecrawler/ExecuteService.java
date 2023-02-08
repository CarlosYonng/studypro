package com.study.example.filecrawler;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ExecuteService {
    private static final int N_COUSUMERS = 5;

    public static void startIndexing(File[] roots) {
        BlockingQueue<File> queue = new LinkedBlockingQueue<File>(1000);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return true;
            }
        };

        for (File root : roots) {
            new Thread(new FileCrawler(queue,fileFilter,root)).start();
        }

        for (int i=0; i < N_COUSUMERS; i++) {
            new Thread(new Indexer(queue)).start();
        }
    }

}
