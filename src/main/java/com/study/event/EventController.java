package com.study.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    private MyPublisher myPublisher;

    /**
     * 事件监听模式，类似监听者模式()
     * 观察者模式定义对象间的一种一对多的依赖关系，当一个对象的状态发生改变时，所有依赖于它的对象都得到通知并被自动更新，其主要解决一个对象状态改变给其他关联对象通知的问题，保证易用和低耦合。一个典型的应用场景是：当用户注册以后，需要给用户发送邮件等其他事件。
     *
     * ApplicationEvent：通过继承它，实现自定义事件。另外，通过它的 source 属性可以获取事件源，timestamp 属性可以获得发生时间。
     *
     * ApplicationEventPublisher：通过实现它，来发布变更事件。
     *
     * ApplicationEventListener：通过实现它，来监听指定类型事件并响应动作。
     * **/
    @RequestMapping("/testListener")
    public void lis() {
        myPublisher.publish("测试开始");
    }
}
