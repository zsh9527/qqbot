package com.zsh.qqbot.service;

import com.zsh.qqbot.client.HttpClient;
import com.zsh.qqbot.config.QqProp;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 调度任务
 *
 * @author zsh
 * @version 1.0.0
 * @date 2023/02/27 18:50
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

    private final Bot bot;

    private final QqProp qqProp;

    private final HttpClient httpClient;

    /**
     * 整点执行任务， 间隔2小时
     */
    @PostConstruct
    void initLoverTask() {
        int delay = (60 - LocalDateTime.now().getMinute()) % 60;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(
            () -> {
                int hour = LocalDateTime.now().getHour();
                if ((hour >= 9 && hour <= 12) || (hour >= 15 && hour <= 21)) {
                    // 非休息时间执行任务
                    bot.getFriend(qqProp.getLoverQQ()).sendMessage(httpClient.buildMessage());
                }
            },
            delay,
            60,
            TimeUnit.MINUTES);
    }

    /**
     * 整点8点打卡
     */
    @PostConstruct
    void initGroupTask() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(
            new Runnable() {

                @SneakyThrows
                @Override
                public void run() {
                    while (LocalDateTime.now().getHour() != 9) {
                        Thread.sleep(1000 * 60 * 10);
                    }
                    bot.getGroups().stream()
                        .filter(group -> qqProp.getGroups() != null && qqProp.getGroups().contains(group.getId()))
                        .forEach(group -> {
                            // 全员禁言
                            group.getSettings().setMuteAll(true);
                            group.sendMessage(new MessageChainBuilder()
                                .append(new PlainText("起床嘘嘘了"))
                                .build()
                            );
                        });
                    // 睡眠一分钟后解除禁言
                    Thread.sleep(1000 * 60);
                    bot.getGroups().stream()
                        .filter(group -> qqProp.getGroups() != null && qqProp.getGroups().contains(group.getId()))
                        .forEach(group -> {
                            // 全员禁言
                            group.getSettings().setMuteAll(false);
                        });

                }
            },
            0,
            1,
            TimeUnit.DAYS);
    }
}

