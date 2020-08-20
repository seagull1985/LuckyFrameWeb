package com.luckyframe.common.utils;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EncryptionUtils {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 默认公钥
     */
    private static final String DEFAULT_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCadmkcep05BmZ3aWH72ab8iw8xD4XYrXmeySwBgKQY4mhHo2MrT8fKiNaG0PC/Jy09inPczBPqf/IPILlE79ujgpc84bHnR27u9IH7kJlyoLiPRGoN+oQbWJakmYTwGkdG4z1Re9xoKi4Ww1WShkvJspMwOWtkwfwub5zkvQtSWQIDAQAB";

    /**
     * 默认私钥
     */
    private static final String DEFAULT_PRIVATE_KEY="MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJp2aRx6nTkGZndpYfvZpvyLDzEPhditeZ7JLAGApBjiaEejYytPx8qI1obQ8L8nLT2Kc9zME+p/8g8guUTv26OClzzhsedHbu70gfuQmXKguI9Eag36hBtYlqSZhPAaR0bjPVF73GgqLhbDVZKGS8mykzA5a2TB/C5vnOS9C1JZAgMBAAECgYBNTjYNKtDFWY6u9O81PRl2C6LuyvYSG8Bi2AxONDPswGOwdvWLF8LGevXjQ286PEFIK6MRPpI5Kw/awmX3OpSR10nAzLHo7KU03+1+71EpGcGt0OAudDG+Qzzz10rjyoBwV21d8utoJmy4m5MLbp7yxxZ0caGNfkJMj7QJyxsQAQJBAOtTwyqdGbhLle0rD/9WhK5huFBAaXCw21mJK/wkByVFk9ynHN1P0e3fgS4S2KOyWGEwMgfaxRxvn+Tmj8sQLkkCQQCoCBhihIZj0epYvdQdf63sgrzVlUr3d3IIlKio4JLfvo4gFGpQjV/mOlyS7AGNWf5iDFzJvpXoXET5GYkmpEORAkA784LtAEjlIpx3Z1kT+76hjlOeXkp+Yw/+p2uFOMh5PliFBi3cU9FvgFkwm6yFR5IscFLOnXVJ4UYi0nofiWfBAkBMZvnneci9hIog9ZeIHjEP9FY2a16d7RLNsgKKXyqJT9TB42Z/3/h1751+NI90HTJclLBwDxeMgr/d3+2Lw27xAkBdQqmrWTAmHPGS48CZ/VYu9repRhDmV+8nsWtX1fdU410kcfYgib7WX9Y22v4vGQrVt72waBvvEvbjWjXH+Ael";

    /**
     * 用户配置的私钥
     */
    private static String USER_PRIVATE_KEY;

    /**
     * 用户配置的公钥
     */
    private static String USER_PUBLIC_KEY;

    @Value("${client.config.privateKey}")
    public static void setUserPrivateKey(String userPrivateKey) {
        USER_PRIVATE_KEY = userPrivateKey;
    }

    @Value("${client.config.publicKey}")
    public static void setUserPublicKey(String userPublicKey) {
        USER_PUBLIC_KEY = userPublicKey;
    }

    /**
     * 获取密钥对
     *
     * @return 密钥对
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(1024);
        return generator.generateKeyPair();
    }

    /**
     * 获取私钥
     *
     * @param privateKey 私钥字符串
     * @return
     */
    public static PrivateKey getPrivateKey(String privateKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * 获取公钥
     *
     * @param publicKey 公钥字符串
     * @return
     */
    public static PublicKey getPublicKey(String publicKey) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        byte[] decodedKey = Base64.decodeBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * RSA加密
     *
     * @param data 待加密数据
     * @param publicKey 公钥
     * @return
     */
    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        int inputLen = data.getBytes().length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data.getBytes(), offset, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data.getBytes(), offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        // 获取加密内容使用base64进行编码,并以UTF-8为标准转化成字符串
        // 加密后的字符串
        return new String(Base64.encodeBase64String(encryptedData));
    }

    /**
     * RSA解密
     *
     * @param data 待解密数据
     * @param privateKey 私钥
     * @return
     */
    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] dataBytes = Base64.decodeBase64(data);
        int inputLen = dataBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(dataBytes, offset, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(dataBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        // 解密后的内容
        return new String(decryptedData, "UTF-8");
    }

    /**
     * 签名
     *
     * @param data 待签名数据
     * @param privateKey 私钥
     * @return 签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {
        byte[] keyBytes = privateKey.getEncoded();
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey key = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initSign(key);
        signature.update(data.getBytes());
        return new String(Base64.encodeBase64(signature.sign()));
    }

    /**
     * 验签
     *
     * @param srcData 原始字符串
     * @param publicKey 公钥
     * @param sign 签名
     * @return 是否验签通过
     */
    public static boolean verify(String srcData, PublicKey publicKey, String sign) throws Exception {
        byte[] keyBytes = publicKey.getEncoded();
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey key = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance("MD5withRSA");
        signature.initVerify(key);
        signature.update(srcData.getBytes());
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    /**
     * 加密
     * @param data
     * @return
     */
    public static String encrypt(String data)
    {
        try
        {
            String publicKey= StringUtils.isNotEmpty(USER_PUBLIC_KEY)?USER_PUBLIC_KEY:DEFAULT_PUBLIC_KEY;
            String encryptData = encrypt(data, getPublicKey(publicKey));
            return encryptData;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 解密
     * @param encryptData
     * @return
     */
    public static String decrypt(String encryptData)
    {
        try
        {
            String privateKey= StringUtils.isNotEmpty(USER_PRIVATE_KEY)?USER_PRIVATE_KEY:DEFAULT_PRIVATE_KEY;
            String data = decrypt(encryptData, getPrivateKey(privateKey));
            return data;
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return encryptData;
    }

    public static void main(String[] args) {
        try {
            // 生成密钥对
            KeyPair keyPair = getKeyPair();
            String privateKey = new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded()));
            String publicKey = new String(Base64.encodeBase64(keyPair.getPublic().getEncoded()));
            System.out.println("私钥:" + privateKey);
            System.out.println("公钥:" + publicKey);
            // RSA加密
            String data = "待加密的文字内容";
            String encryptData = encrypt(data, getPublicKey(publicKey));
            System.out.println("加密后内容:" + encryptData);
            // RSA解密
            String decryptData = decrypt(encryptData, getPrivateKey(privateKey));
            System.out.println("解密后内容:" + decryptData);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("加解密异常");
        }
    }
}