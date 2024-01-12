package com.zsh.qqbot.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;

/**
 * EncodeUtils
 *
 * @author zsh
 * @version 1.0.0
 * @date 2024/01/09 17:22
 */
public class EncodeUtils {

    private static Cipher cipher;

    static {
        Security.addProvider(new BouncyCastleProvider());
        byte[] key = new byte[16]; // 16 字节的 AES 密钥
        byte[] iv = new byte[16];  // 16 字节的初始向量 IV
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            // 初始化 Cipher 为模式
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密
     */
    public static String decode(String ciphertext) throws Exception {
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }
}
