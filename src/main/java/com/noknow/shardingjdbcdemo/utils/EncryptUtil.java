package com.noknow.shardingjdbcdemo.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wang.jianwen
 * @version 1.0
 * @date 2021/05/15
 */
@Slf4j
public final class EncryptUtil {

  private static final String KEY = "zK8Gv+ycuzumesWziXH4yA==";

  public EncryptUtil() {
  }

  private static byte[] encrypt(byte[] content) {
    byte[] encrypted = new byte[0];
    try {
      SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(KEY), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
      cipher.init(1, skeySpec, iv);
      encrypted = cipher.doFinal(content);
    } catch (Exception e) {
      log.error("aes encrypt error:", e);
    }
    return encrypted;
  }

  private static String decrypt(byte[] content) {
    byte[] original = new byte[0];
    try {
      SecretKeySpec skeySpec = new SecretKeySpec(Base64.getDecoder().decode(KEY), "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
      cipher.init(2, skeySpec, iv);
      original = cipher.doFinal(content);
    } catch (Exception e) {
      log.error("aes decrypt error:", e);
    }
    return new String(original, StandardCharsets.UTF_8);
  }

  public static String encrypt(String content) {
    return Base64.getEncoder().encodeToString(encrypt(content.getBytes(StandardCharsets.UTF_8)));
  }

  public static String decrypt(String encryptStr) {
    return encryptStr != null && !"".equals(encryptStr) ? decrypt(
        Base64.getDecoder().decode(encryptStr)) : encryptStr;
  }
}
