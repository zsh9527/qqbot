package com.zsh.qqbot.handler;

import com.zsh.qqbot.config.QqProp;
import com.zsh.qqbot.pojo.GroupDTO;
import lombok.RequiredArgsConstructor;
import net.mamoe.mirai.event.Event;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * 群消息
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/27 18:47
 */
@Component
@RequiredArgsConstructor
public class GroupMessageEventHandler implements EventHandler {

    private final QqProp qqProp;

    // 组id + 成员id为前缀
    public static final Map<String, GroupDTO> map = new HashMap();

    // map数据大小， 逐步递增清除
    private int maxSize = 500;
    private int stepSize = 100;

    @Override
    public Class<GroupMessageEvent> getSupportEvent() {
        return GroupMessageEvent.class;
    }

    @Override
    public boolean filter(Event event) {
        GroupMessageEvent realEvent = (GroupMessageEvent) event;
        Long groupId = realEvent.getGroup().getId();
        return qqProp.getGroups() != null && qqProp.getGroups().contains(groupId);
    }

    /**
     * 请求评论太过频繁的人禁言或者踢出
     */
    @Override
    public void handle(Event event) {
        GroupMessageEvent realEvent = (GroupMessageEvent) event;
        long groupId = realEvent.getGroup().getId();
        long memberId = realEvent.getSender().getId();
        String content = realEvent.getMessage().contentToString();
        long now = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        GroupDTO groupDTO;
        if (map.containsKey(groupId + "-" + memberId)) {
            groupDTO = map.get(groupId + "-" + memberId);
        } else {
            groupDTO = new GroupDTO(now, 0, null);
            map.put(groupId + "-" + memberId, groupDTO);
            // map数据过大, 清除旧的数据
            if (map.size() > maxSize) {
                map.entrySet().stream().forEach(entry -> {
                    if (now - entry.getValue().getStartTime() > 60) {
                        map.remove(entry.getKey());
                    }
                });
            }
            maxSize += stepSize;
        }
        // 超过了一个计数周期, 重新计算
        if (now - groupDTO.getStartTime() > 60) {
            groupDTO.setStartTime(now).setFrenquency(0).setContent(null);
        }
        groupDTO.setFrenquency(groupDTO.getFrenquency() + 1);
        if (content.length() > 20) {
            if (groupDTO.getContent() == null) {
                groupDTO.setContent(new ArrayList<>());
            }
            groupDTO.getContent().add(content);
        }
        // 发送过长内容超过5次, 且重复次数超过1/3, 判断为专门打广告的
        if (groupDTO.getContent() != null && groupDTO.getContent().size() >= 5) {
            if (new HashSet<>(groupDTO.getContent()).size() >= groupDTO.getContent().size() / 3) {
                realEvent.getGroup().get(memberId).kick("打广告");
            }
        }
        // 发送频率一分钟超过10次, 且长内容超过2次, 判断为刷屏
        if (groupDTO.getFrenquency() >= 10 && groupDTO.getContent() != null && groupDTO.getContent().size() >= 2) {
            // 禁言5分钟
            realEvent.getGroup().get(memberId).mute(60*5);
        }
        // 发送频率一分钟超过30次, 禁言一天
        if (groupDTO.getFrenquency() >= 30) {
            realEvent.getGroup().get(memberId).mute(60*60*24);
        }
    }
}
