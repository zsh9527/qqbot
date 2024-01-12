package com.zsh.qqbot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsh.qqbot.pojo.serialize.LicenseDTO;
import com.zsh.qqbot.utils.EncodeUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;

@Component
@RequiredArgsConstructor
public class LicenseValidator {

    public static String signData;

    @Value("${license.code}")
    private String activationCode;

    private final ObjectMapper objectMapper;

    private final ConfigurableApplicationContext context;

    @SneakyThrows
    @PostConstruct
    void init() {
        // 验证许可证
        var content = EncodeUtils.decode(activationCode);
        LicenseDTO license = objectMapper.readValue(content, LicenseDTO.class);
        Instant currentTime = Instant.ofEpochMilli(getWebsiteDatetime("http://www.baidu.com"));
        // 2. 许可证验证
        Instant expireTime = Instant.ofEpochSecond(license.getExpireTime());
        if (expireTime.isBefore(currentTime)) {
            System.out.println("证书已过期");
            context.close();
        } else {
            signData = license.getSignData();
        }
    }

    /**
     * 获取指定网站的日期时间
     */
    @SneakyThrows
    private static long getWebsiteDatetime(String webUrl){
        URL url = new URL(webUrl);// 取得资源对象
        URLConnection uc = url.openConnection();// 生成连接对象
        uc.connect();// 发出连接
        return uc.getDate();
    }
}