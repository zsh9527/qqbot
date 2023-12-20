package com.zsh.qqbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * QqProp
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "qq.config")
public class QqProp {

    /**
     * qq用户名
     */
    private Long username;

    /**
     * qq密码
     */
    private String password;

    /**
     * 定时发送消息的好友qq
     */
    private Long loverQQ;

    /**
     * 定时发送消息的好友qq
     */
    private Long broQQ;

    /**
     * 定时发送的消息内容
     */
    private String broContent;

    /**
     * 管理的群列表
     */
    private List<Long> groups;
}
