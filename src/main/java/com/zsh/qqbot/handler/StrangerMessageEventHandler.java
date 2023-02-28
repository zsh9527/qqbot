package com.zsh.qqbot.handler;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.StrangerMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

/**
 * 陌生人消息
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/27 18:47
 */
@Component
public class StrangerMessageEventHandler implements EventHandler {

    @Override
    public Class<StrangerMessageEvent> getSupportEvent() {
        return StrangerMessageEvent.class;
    }

    @Override
    public void handle(Event event) {
        StrangerMessageEvent realEvent = (StrangerMessageEvent) event;
        realEvent.getSubject().sendMessage(new MessageChainBuilder()
                .append(new PlainText("不和陌生人说话"))
                .build()
        );
    }
}
