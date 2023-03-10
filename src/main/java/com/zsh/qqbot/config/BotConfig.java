package com.zsh.qqbot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BotConfig {

    private final QqProp qqProp;

    /**
     * 登录qq机器人 -- 尝试不同协议登录
     */
    @Bean
    public Bot loginBot() {
        Bot bot = null;
        BotConfiguration.MiraiProtocol[] protocols = BotConfiguration.MiraiProtocol.values();
        for (BotConfiguration.MiraiProtocol protocol : protocols) {
            bot = BotFactory.INSTANCE.newBot(qqProp.getUsername(), qqProp.getPassword(), new BotConfiguration() {{
                // 不同协议使用不同设备信息
                fileBasedDeviceInfo("device/" + protocol.name() + ".json");
                // 修改缓存目录到build/cache目录下
                setCacheDir(new File("build/cache"));
                setProtocol(protocol);
            }});
            try {
                bot.login();
                break;
            } catch (Exception e) {
                log.info("login bot failed", e);
            }
        }
        return bot;
    }
}
