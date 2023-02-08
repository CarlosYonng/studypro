package com.study.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class MyListener {

    @Async
    @Order(2)
    @EventListener(MyEvent.class)
    public void handle1(MyEvent event) throws InterruptedException {
        String msg = event.getMsg();
        System.out.println("事件监听1内容为：" + msg);
        Thread.sleep(2000);
    }

    @Async
    @Order(1)
    @EventListener(MyEvent.class)
    public void handle2(MyEvent event) throws InterruptedException {
        String msg = event.getMsg();
        System.out.println("事件监听2内容为：" + msg);
        Thread.sleep(2000);
    }

}
