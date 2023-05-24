package com.zsh.qqbot;

import com.zsh.qqbot.config.MyRuntimeHints;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportRuntimeHints;

@SpringBootApplication
@ImportRuntimeHints(value = MyRuntimeHints.class)
public class QQbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(QQbotApplication.class, args);
    }

}
