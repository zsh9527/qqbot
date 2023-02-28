package com.zsh.qqbot.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * GroupDTO
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/28 19:06
 */
@Getter
@Setter
@AllArgsConstructor
@Accessors(chain = true)
public class GroupDTO {

    /**
     * 发送消息计算时间
     */
    private Long startTime;

    /**
     * 发送消息频次
     */
    private Integer frenquency;

    /**
     * 发送消息内容
     */
    private List<String> content;
}
