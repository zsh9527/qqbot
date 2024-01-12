package com.zsh.qqbot.config;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.auth.BotAuthorization;
import net.mamoe.mirai.utils.BotConfiguration;
import org.jetbrains.annotations.Nullable;
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
    private final ConfigFileProp configFileProp;

    /**
     * 登录qq机器人 -- 尝试不同协议登录
     */
    @Bean(name = "bot")
    public Bot loginBot() {
        BotConfiguration.MiraiProtocol[] protocols = BotConfiguration.MiraiProtocol.values();
        return getBotByQRcode(protocols);
    }

    /**
     * 二维码登录
     */
    @Nullable
    private Bot getBotByQRcode(BotConfiguration.MiraiProtocol[] protocols) {
        Bot bot = null;
        for (BotConfiguration.MiraiProtocol protocol : protocols) {
            String finalDirName = configFileProp.getQqDeviceDir();
            bot = BotFactory.INSTANCE.newBot(qqProp.getUsername(), BotAuthorization.byQRCode(), new BotConfiguration() {{
                // 不同协议使用不同设备信息
                fileBasedDeviceInfo(finalDirName + protocol.name() + ".json");
                // 修改缓存目录到build/cache目录下
                setCacheDir(new File(configFileProp.getQqCacheDir()));
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

    /**
     * 密码登录
     */
    @Nullable
    private Bot getBotByPassword(BotConfiguration.MiraiProtocol[] protocols) {
        Bot bot = null;
        for (BotConfiguration.MiraiProtocol protocol : protocols) {
            String finalDirName = configFileProp.getQqDeviceDir();
            bot = BotFactory.INSTANCE.newBot(qqProp.getUsername(), qqProp.getPassword(), new BotConfiguration() {{
                // 不同协议使用不同设备信息
                fileBasedDeviceInfo(finalDirName + protocol.name() + ".json");
                // 修改缓存目录到build/cache目录下
                setCacheDir(new File(configFileProp.getQqCacheDir()));
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
