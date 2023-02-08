
/**
 * java中的事件机制的参与者有3种角色：
 *
 * Event Eource：
 * 具体的事件源，比如说，你点击一个button，那么button就是event source，要想使button对某些事件进行响应，你就需要注册特定的listener。
 * Event Object：
 * 事件状态对象，用于 listener 的相应的方法之中，作为参数，一般存在于listerner 的方法之中
 * Event Listener：
 * 当它监听到event object产生的时候，它就调用相应的方法，进行处理。
 * 例子如下，Spring-context 的事件机制的根类：
 * ————————————————
 * 版权声明：本文为CSDN博主「viagra2009100129」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/m0_37941483/article/details/90648418
 * **/

package com.study.event;

import java.util.EventObject;

public abstract class ApplicationEvent extends EventObject {

    public static final long serialVersionUID = 7099057708183571937L;

    public final long timestamp = System.currentTimeMillis();

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public ApplicationEvent(Object source) {
        super(source);
    }

    public final long getTimeStamp() {
        return this.timestamp;
    }

}
