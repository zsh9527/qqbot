package com.zsh.qqbot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置文件属性
 */
@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "file.config")
public class ConfigFileProp {

    /**
     * qq 缓存目录
     */
    private String qqCacheDir;

    /**
     * qq 登录设置目录
     */
    private String qqDeviceDir;
}
