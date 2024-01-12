package com.zsh.qqbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsh.qqbot.pojo.serialize.LicenseDTO;
import com.zsh.qqbot.utils.EncodeUtils;
import lombok.SneakyThrows;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

/**
 * LicenseTest
 *
 * @author zsh
 * @version 1.0.0
 * @date 2024/01/09 16:56
 */
@JsonTest
public class LicenseTest {

    @Autowired
    ObjectMapper objectMapper;

    /**
     * 测试encode工具类
     */
    @SneakyThrows
    @Test
    void testEncodeUtils() {
        String content = "testas中文，123d123123{,?1";
        var ciphertext = encode(content);
        Assertions.assertNotNull(ciphertext);
        var decodeContent = EncodeUtils.decode(ciphertext);
        Assertions.assertEquals(content, decodeContent);
    }

    /**
     * 测试生成一个月的证书
     */
    @SneakyThrows
    @Test
   void testGenerateOneMonthLicense() {
        LicenseDTO license = new LicenseDTO();
        license.setExpireTime(Instant.now().plus(51, ChronoUnit.DAYS).getEpochSecond());
        license.setSignData("haha");
        String content = objectMapper.writeValueAsString(license);
        var ciphertext = encode(content);
        System.out.println(ciphertext);
   }

    /**
     * 测试过期证书
     */
    @SneakyThrows
    @Test
    void testGenerateExpireLicense() {
        LicenseDTO license = new LicenseDTO();
        license.setExpireTime(Instant.now().plus(-1, ChronoUnit.DAYS).getEpochSecond());
        license.setSignData("haha");
        String content = objectMapper.writeValueAsString(license);
        var ciphertext = encode(content);
        System.out.println(ciphertext);
    }

    /**
     * 加密
     */
    @SneakyThrows
    public String encode(String content) {
        Cipher encodeCipher;
        Security.addProvider(new BouncyCastleProvider());
        byte[] key = new byte[16]; // 16 字节的 AES 密钥
        byte[] iv = new byte[16];  // 16 字节的初始向量 IV
        // 创建 Cipher 实例, 使用 AES 算法进行加密
        encodeCipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        // 初始化 Cipher 为模式
        encodeCipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        byte[] encodeBytes = content.getBytes();
        byte[] encryptedBytes = encodeCipher.doFinal(encodeBytes);
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }
}
