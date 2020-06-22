package com.luckyframe.common.utils.EncryptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * <p>
 * RSA公钥/私钥/签名工具包
 * </p>
 * <p>
 * 字符串格式的密钥在未在特殊说明情况下都为BASE64编码格式<br/>
 * 由于非对称加密速度极其缓慢，一般文件不使用它来加密而是使用对称加密，<br/>
 * 非对称加密算法可以用来对对称加密的密钥加密，这样保证密钥的安全也就保证了数据的安全
 * </p>
 */
@Component
public class EncryptionUtils {

    /**
     * 获取公钥的key
     */
    private static String USER_PUBLIC_KEY;

    /**
     * 获取私钥的key
     */
    private static String USER_PRIVATE_KEY;

    @Value("${client.config.publicKey}")
    public  void setUserPublicKey(String publicKey) {
        EncryptionUtils.USER_PUBLIC_KEY = publicKey;
    }

    @Value("${client.config.privateKey}")
    public void setUserPrivateKey(String privateKey) {
        EncryptionUtils.USER_PRIVATE_KEY = privateKey;
    }

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 默认私钥
     */
    private static final String DEFAULT_PRIVATE_KEY_STRING = "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQDvfs8va6a/gNAlL2qKrhWZVoEKrwORnbS0KQKFAUyK5IzzWjVouU1Fy2N0Eb3M8FuV+Ls/johsXGiSQrEUFy61hOTCmXcck8Z5tEvNOOw2g/K2PFFjk/46/ryev8txZcKYNAwGtkWnGyXjeuZXrGkhE016Fe5j2ZTvA1AYlCNr2KBJIsmV32y4WGyzytC24ojwmhoX897Gs50UCcI+Ah66mNLVcoe8ruuaXEpSVY/w+Vx//BtpyxBXx5l18k3/0bwfYsK0fsB2jIN/PX3RHefXjzOfsJhagWoqai3mq9bQorGlJTs0sbHywI+m2ozhaPGvEAoVbLSYcCubglndjomZAgMBAAECggEBAOU68wPofgn+OTveTpO+XF4QNiMLsNbHpBZsykSUIbcvWtnyFfzOn++V5PNLp+doh8Db+h2zR1Cwgka8HtGj/skvEeZRDXkE2IvUzlj3iuADG2TiBuTQ2h2NhOxNl2RKih80ce5auABiM7mwAJkaJOXLm/Sm2S4pLCw5d4iPAfhN3dDHstjYaFqksPkuEqad9J/RZICAVXbsqjut/5faqre5M3Bs5frdbYKOQvdw44r1r7H8xKzjHYtLldktraJDbcVyzwgTXNX0rUnmU3d8PWRF764ShXo/KJdXGvbRUfNhtaZElv2XOM8A86uAeXEFYXcze/zkCx0mPXwD7cxfwIECgYEA+xmUyAPmZFs4C6NKScxDEq+92Qi3g3+smA3Z9BEneNszdTJsyz8n3IIi0/0moFfJwduSuO7AD9KBgvYwse8jXrC6px5guH3K3oGHEc5H9uV9O213PLII+z/t/4qjOTlM5EZ3AN1Sp3270yMqTSFIcTj7xo7LEeBYo4uh1zxpTYUCgYEA9CtBVOutvUqgadKzm15DR+ao2N6A/ipzcR+N0iJuDBYqMWxunG4ad5qTfEBnJSjWCY1HLJIPsYyl16nC0o5wuUUf+enidaJLxMwBlyYyFtDzXIKIBwM/g8CIUSv0/Y9J4ZRg/eY3rD1YToCJijduS0HrLa6op7WjQg23Zd1TzgUCgYEAs3PLzcpjvHMoscCIVgeCI/evMU22gWccfSrhCjm8QPY/Z1GbSQIgxcnHhENWeJ9k1IPM7xfJ8UTbRDIYCQJD/+dD7i2aqB9WfgCJ/GK1MbE+99q6rYj7PDyrJcb58eqOllH5uId0C7mjIzjtgWP49tnnrjbc0DDKiE9BYaaOB1UCgYEAiInHtt00Aq5AleylUzSbGxH5SYzjRT4n4BJtFBz8vPWIEqs4D3Hiiw99efXpt/Xl+uFt85aaAAdOlhLwRx4wGup0vyPy5yUiiaIrIYLSb7/Y1tqHhqRiWCLKF9ok3cXK4sI+mryqVT7Yqs1mHExy+NKb2Kfa2C3SZZWWAPiLbKECgYEA7fTcNKh7gZYLgPOjpLGOsoiYXl7dPzfcwynI6X4DilrO4SW7VAY/WiEU+BjIZ13NuWuKB0Rb070tg+yK6cuGl4TE2sIGODoz1y4KdK/Lm/Xyl4etsM/wBkehBRznvCI/MikKOGb8mDyBUxGV8ceyXwnIQV7Nk1L24EK/IvFFl0Y=";

    /**
     * 默认公钥
     */
    public static final String DEFAULT_PUBLIC_KEY_STRING = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA737PL2umv4DQJS9qiq4VmVaBCq8DkZ20tCkChQFMiuSM81o1aLlNRctjdBG9zPBblfi7P46IbFxokkKxFBcutYTkwpl3HJPGebRLzTjsNoPytjxRY5P+Ov68nr/LcWXCmDQMBrZFpxsl43rmV6xpIRNNehXuY9mU7wNQGJQja9igSSLJld9suFhss8rQtuKI8JoaF/PexrOdFAnCPgIeupjS1XKHvK7rmlxKUlWP8Plcf/wbacsQV8eZdfJN/9G8H2LCtH7AdoyDfz190R3n148zn7CYWoFqKmot5qvW0KKxpSU7NLGx8sCPptqM4WjxrxAKFWy0mHArm4JZ3Y6JmQIDAQAB";

    /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";

    /**
     * 默认编码
     */
    public static final String CHARSET = "UTF-8";

    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";


    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;


    private static final int JS_BASE64_LENGTH = 172;

    /**
     * <p>
     * 生成密钥对(公钥和私钥)
     * </p>
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> genKeyPair() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // 修复漏洞：大多数密码系统都需要足够大的密钥来抵抗暴力攻击。
        keyPairGen.initialize(2048);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * <P>
     * 默认私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData) throws Exception {
        return decryptByPrivateKey(encryptedData, null);
    }

    /**
     * <P>
     * 私钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param privateKey    私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        if (null == privateKey || "".equals(privateKey)) {
            privateKey = DEFAULT_PRIVATE_KEY_STRING;
        }
        byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
        encryptedData = Base64Utils.decode(encryptedData);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    public static String decryptByPrivateKeyForJs(String encryptedData) throws Exception {

        byte[] keyBytes = Base64Utils.decode(DEFAULT_PRIVATE_KEY_STRING.getBytes(CHARSET));
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateK);

        if (encryptedData.length() > JS_BASE64_LENGTH) {
            String str = "";
            for (int i = 0; i < encryptedData.length(); i += JS_BASE64_LENGTH) {
                int offset = i + JS_BASE64_LENGTH;
                String s = encryptedData.substring(i, offset > encryptedData.length() ? encryptedData.length() : offset);
                str += new String(decryptByPrivateKey(s.getBytes(CHARSET)), CHARSET);
            }
            return str;
        } else {
            return new String(decryptByPrivateKey(encryptedData.getBytes(CHARSET)), CHARSET);
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * <p>
     * 默认公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData) throws Exception {
        return decryptByPublicKey(encryptedData, null);
    }

    /**
     * <p>
     * 公钥解密
     * </p>
     *
     * @param encryptedData 已加密数据
     * @param publicKey     公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        if (null == publicKey || "".equals(publicKey)) {
            publicKey = DEFAULT_PUBLIC_KEY_STRING;
        }
        byte[] keyBytes = Base64Utils.decode(publicKey.getBytes());
        encryptedData = Base64Utils.decode(encryptedData);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }

    /**
     * <p>
     * 默认公钥加密
     * </p>
     *
     * @param data 源数据
     * @return base64加密的公钥加密结果
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data) throws Exception {
        return encryptByPublicKey(data, null);
    }

    /**
     * <p>
     * 公钥加密
     * </p>
     *
     * @param data      源数据
     * @param publicKey 公钥(BASE64编码)
     * @return base64加密的公钥加密结果
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        if (null == publicKey || "".equals(publicKey)) {
            publicKey = DEFAULT_PUBLIC_KEY_STRING;
        }
        byte[] keyBytes = Base64Utils.decode(publicKey.getBytes());
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();

        out.close();
        return Base64Utils.encode(encryptedData);
    }

    /**
     * <p>
     * 默认私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data) throws Exception {
        return encryptByPrivateKey(data, null);
    }

    /**
     * <p>
     * 私钥加密
     * </p>
     *
     * @param data       源数据
     * @param privateKey 私钥(BASE64编码)
     * @return base64加密的私钥加密结果
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        if (null == privateKey || "".equals(privateKey)) {
            privateKey = DEFAULT_PRIVATE_KEY_STRING;
        }
        byte[] keyBytes = Base64Utils.decode(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return Base64Utils.encode(encryptedData);
    }

    /**
     * <p>
     * 获取私钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return new String(Base64Utils.encode(key.getEncoded()));
    }

    /**
     * <p>
     * 获取公钥
     * </p>
     *
     * @param keyMap 密钥对
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return new String(Base64Utils.encode(key.getEncoded()));
    }

    /**
     * 使用系统默认公钥加密
     *
     * @param data
     * @return
     */
    public static String encrypt(String data) {
        try {
            byte[] bytes = encryptByPublicKey(data.getBytes(CHARSET), StringUtils.isEmpty(PUBLIC_KEY)?DEFAULT_PUBLIC_KEY_STRING:PUBLIC_KEY);
            return new String(bytes, CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("RSA加密失败", e);
        }
    }

    /**
     * 使用系统默认密钥解密
     *
     * @param data
     * @return
     */
    public static String decrypt(String data) {
        try {
            byte[] bytes = decryptByPrivateKey(data.getBytes(CHARSET), StringUtils.isEmpty(PRIVATE_KEY)?DEFAULT_PRIVATE_KEY_STRING:PRIVATE_KEY);
            return new String(bytes, CHARSET);
        } catch (Exception e) {
            throw new RuntimeException("RSA加密失败", e);
        }
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> stringObjectMap = EncryptionUtils.genKeyPair();
        //获取私钥
        System.out.println(getPrivateKey(stringObjectMap));
        System.out.println("###");
        //获取公钥
        System.out.println(getPublicKey(stringObjectMap));
        String encrypt = encrypt("你好，易招标");
        System.out.println("密文：" + encrypt);
        System.out.println("解密：" + decrypt(encrypt));
    }

}
