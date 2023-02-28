package com.zsh.qqbot.config;

import lombok.AllArgsConstructor;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * BotConfig
 */
@Configuration
@AllArgsConstructor
public class BotConfig {

    private final QqProp qqProp;

    /**
     * 登记qq机器人
     */
    @Bean
    public Bot loginBot() {
        // 使用自定义配置
        Bot bot = BotFactory.INSTANCE.newBot(qqProp.getUsername(), qqProp.getPassword(), new BotConfiguration() {{
            fileBasedDeviceInfo(); // 使用 device.json 存储设备信息
            setCacheDir(new File("build/cache")); // 修改缓存目录到build/cache目录下
            setProtocol(MiraiProtocol.ANDROID_PAD); // 登录协议
        }});
        bot.login();
        return bot;
    }
}
