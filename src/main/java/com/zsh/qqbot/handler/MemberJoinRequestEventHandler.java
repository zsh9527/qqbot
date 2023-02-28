package com.zsh.qqbot.handler;

import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.MemberJoinRequestEvent;
import org.springframework.stereotype.Component;

/**
 * 新加群请求消息
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/27 18:47
 */
@Component
public class MemberJoinRequestEventHandler implements EventHandler {

    // 验证通过关键字
    public static final String VERIFY_KEY = "ice";

    @Override
    public Class<MemberJoinRequestEvent> getSupportEvent() {
        return MemberJoinRequestEvent.class;
    }

    /**
     * 自动申请通过或拒绝群验证消息
     */
    @Override
    public void handle(Event event) {
        MemberJoinRequestEvent realEvent = (MemberJoinRequestEvent) event;
        String verifyMessage = realEvent.getMessage();
        if (verifyMessage.contains(VERIFY_KEY)) {
            realEvent.accept();
        } else {
            realEvent.reject(false);
        }
    }
}
