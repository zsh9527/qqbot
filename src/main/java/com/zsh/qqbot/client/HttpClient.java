package com.zsh.qqbot.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;
import net.mamoe.mirai.message.data.PlainText;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class HttpClient {

    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    /**
     * 获取情话
     */
    @SneakyThrows
    public String getLoveWord() {
        Request.Builder requestBuilder = new Request.Builder()
            .url("https://api.vvhan.com/api/love?type=json");
        requestBuilder.get();
        Response response = requestContent(requestBuilder.build());
        return objectMapper.readValue(response.body().byteStream(), Map.class).get("ishan").toString();
    }

    public MessageChain buildMessage() {
        String word = getLoveWord();
        return new MessageChainBuilder()
            .append(new PlainText("小仙女\n"))
            .append(new PlainText(word + "\n\n\n"))
            .append(new PlainText("站起来活动一下\n"))
            .append(new PlainText("喝点水\n"))
            .append(new PlainText("不要翘二郎腿"))
            .build();
    }

    private Response requestContent(Request request) throws IOException {
        Response response = okHttpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            log.warn(request.url() + ", 请求失败: " + response.message());
            throw new IOException("请求失败");
        }
        return response;
    }
}
