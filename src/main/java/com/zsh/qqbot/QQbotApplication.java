package com.zsh.qqbot;

import com.zsh.qqbot.config.MyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(value = MyRuntimeHints.class)
public class QQbotApplication {

    public static void main(String[] args) {
        // 允许弹出图形化界面
        System.setProperty("java.awt.headless", "false");
        SpringApplication.run(QQbotApplication.class, args);
    }

}
