package com.study.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class MyPublisher {

    @Autowired
    ApplicationEventPublisher applicationEventPublisher;

    public void publish(String msg) {
        applicationEventPublisher.publishEvent(new MyEvent(this,msg));
    }
}
