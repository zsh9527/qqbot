package com.zsh.qqbot.service;

import com.zsh.qqbot.handler.EventHandler;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.event.Listener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 注册事件监听器
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/27 18:50
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventListener {

    private final Bot bot;

    private EventHandler[] handlers;

    private List<Listener> listeners;

    /**
     * 处理器注入
     *
     * @param handlers 所有的处理器
     */
    @Autowired(required = false)
    public void setHandlers(EventHandler[] handlers) {
        this.handlers = handlers;
    }

    /**
     * 初始化
     */
    @PostConstruct
    void listen() {
        this.listeners = Arrays.stream(handlers)
            .map(handler -> bot.getEventChannel()
                .exceptionHandler(exception -> {
                    log.info(exception.getMessage(), exception);
                })
                .filter(handler::filter)
                .subscribeAlways(handler.getSupportEvent(), handler::handle)
            )
            .collect(Collectors.toList());
    }

    /**
     * 析构函数
     */
    @PreDestroy
    void destroy() {
        this.listeners.stream().forEach(listener -> {
            if (!listener.isCompleted()) {
                listener.complete();
            }
        });
    }

}
