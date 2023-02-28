package com.zsh.qqbot.handler;

import com.zsh.qqbot.client.HttpClient;
import com.zsh.qqbot.config.QqProp;
import lombok.RequiredArgsConstructor;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import org.springframework.stereotype.Component;

/**
 * 特定的好友消息
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/27 18:47
 */
@Component
@RequiredArgsConstructor
public class SpecialFriendEventHandler implements EventHandler {

    private final QqProp qqProp;
    private final HttpClient httpClient;

    @Override
    public Class<FriendMessageEvent> getSupportEvent() {
        return FriendMessageEvent.class;
    }

    @Override
    public boolean filter(Event event) {
        FriendMessageEvent friendMessageEvent = (FriendMessageEvent) event;
        return friendMessageEvent.getSender().getId() == qqProp.getLoverQQ();
    }

    @Override
    public void handle(Event event) {
        FriendMessageEvent realEvent = (FriendMessageEvent) event;
        realEvent.getSubject().sendMessage(httpClient.buildMessage());
    }
}
