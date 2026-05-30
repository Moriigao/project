package com.project.demo.util;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;


/**
 * RSA解密工具类
 */
public class RsaUtils {
    public static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAL6YQPNgtso0H2ts668aDB0FSk9oevJEZqvEFCmmQ7OWP6i56nwwyrVeDs0GSMs5Xa+efgTkBW9fRdU6YGDxbl41DW5LUcIbZ+J7wGlcaCXWlaP8dgisDZ39TvHG8w7xQyEAZ8x1oas+sZ1lOv3mnMv+r5Ulk5ypICC7H9gR27XXAgMBAAECgYEAgKuhDa15OcE+hPOfmTLogp8Tr9BFUFFFTyisxAFvK0p/55S77OOztgWt0FQxyKEN0oYZZYva73lOV8l1T30umFiKV55s7nOFeH/C+x9J8CuyBxceFyR/brU75dQkB95rO2uhg21ZMyEIPv+QNBuQy8d1hgnUCSmUw2VRAHTuSAECQQDmlDnpjQ/TP4YvIGXj3/a0hQfhp/5oUrN868RZKBD3HURF4DUJnzvIb7NuHr8zHMUifu6abRPvJJ9P6u5C7m8BAkEA05uG3TGzjVwce647DO13kMZeoGkYKMxfGVtQVJP0Z4KjLNWX8Atpo8k9K6EqPi0O1+yHKdzZJNQzYxSptdh81wJAWQxZqUbfG7hmvACJGQ4/msvdlVppuCRoSRBMjhoZIzZcTZgfI44pamkpJJgfQ0ATKhVXVLBXiH3eQOD8D0FCAQJAHwdtTT4egBoDqzcvPYQMxlPwSEFg44qkX0l+jAhuDEehuc9QSkjCItw9dgpZ8WgWBx+N6luSE85yApNjF2E5XQJAE6f2z8NyiiRNnq3IvdE1k2+kIU9I1ZEzWtF/I8KFlr7EJ+mZjJ3eam2rMpUw2fJnF4U7ILDiIPJ8QHwjTnkl1A==";

    /**
     * 私钥解密
     */
    public static String decryptByPrivateKey(String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey)); //将解码成字节数组并使用容器进行存储
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);   //将之前的容器对象变成私钥对象
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);   //获取 cipher 实例并传入私钥，设置成解密模式
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }
    /**
     * 构建RSA密钥对
     */
    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);  //指定密钥的大小为 1024 位
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }

    /**
     * RSA密钥对对象
     */
    public static class RsaKeyPair {
        private final String publicKey;
        private final String privateKey;
        public RsaKeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }
        public String getPublicKey() {
            return publicKey;
        }
        public String getPrivateKey() {
            return privateKey;
        }
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        RsaKeyPair keyPair = RsaUtils.generateKeyPair();
        System.out.println(keyPair.getPublicKey());
        System.out.println(keyPair.getPrivateKey());
    }
}
