package com.zsh.qqbot.handler;

import com.zsh.qqbot.config.QqProp;
import lombok.RequiredArgsConstructor;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MemberJoinEvent;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Component;

/**
 * 群成员加入事件
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/27 18:47
 */
@Component
@RequiredArgsConstructor
public class MemberJoinEventHandler implements EventHandler {

    private final QqProp qqProp;

    @Override
    public Class<MemberJoinEvent> getSupportEvent() {
        return MemberJoinEvent.class;
    }

    @Override
    public boolean filter(Event event) {
        MemberJoinEvent realEvent = (MemberJoinEvent) event;
        Long groupId = realEvent.getGroupId();
        return qqProp.getGroups() != null && qqProp.getGroups().contains(groupId);
    }

    /**
     * 自动@他
     */
    @Override
    public void handle(Event event) {
        MemberJoinEvent realEvent = (MemberJoinEvent) event;
        realEvent.getGroup().sendMessage(new MessageChainBuilder()
            .append(new At(realEvent.getMember().getId()))
            .append(new PlainText("\n欢迎加入"))
            .build()
        );
    }
}
