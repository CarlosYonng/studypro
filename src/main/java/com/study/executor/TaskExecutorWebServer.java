package com.study.executor;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskExecutorWebServer {

    //固定线程池可容纳一百个线程
    private static final int NTHREADS = 100;

    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        while (true) {
            final  Socket connect = serverSocket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    handleRequest(connect);
                }
            };
            exec.execute(task);
        }
    }

    public static void handleRequest(Socket socket) {
        //todo
    }
}
