package com.zsh.qqbot.handler;

import net.mamoe.mirai.event.Event;

/**
 * 事件监听器
 */
public interface EventHandler<T extends Event> {

    // 获取支持的事件
    Class<T> getSupportEvent();

    // 过滤事件
    default boolean filter(T event) {
        return true;
    }

    // 处理事件
    void handle(T event);
}
