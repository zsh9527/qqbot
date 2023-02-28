package com.zsh.qqbot.handler;

import com.zsh.qqbot.config.QqProp;
import lombok.RequiredArgsConstructor;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.FriendMessageEvent;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

/**
 * 好友消息
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/27 18:47
 */
@Component
@RequiredArgsConstructor
public class FriendMessageEventHandler implements EventHandler {

    private final QqProp qqProp;

    @Override
    public Class<FriendMessageEvent> getSupportEvent() {
        return FriendMessageEvent.class;
    }

    @Override
    public boolean filter(Event event) {
        FriendMessageEvent friendMessageEvent = (FriendMessageEvent) event;
        return friendMessageEvent.getSender().getId() != qqProp.getLoverQQ();
    }

    @Override
    public void handle(Event event) {
        FriendMessageEvent realEvent = (FriendMessageEvent) event;
        long friendName = realEvent.getSender().getId();
        String friendContent = realEvent.getMessage().contentToString();
        realEvent.getSubject().sendMessage(new MessageChainBuilder()
                .append(new PlainText("自动回复好友消息\n"))
                .append(new PlainText("好友qq" + friendName + "\n"))
                .append(new PlainText("回复内容" + friendContent))
                .build()
        );
    }
}
