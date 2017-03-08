package com.ch.study.other;

import com.alibaba.fastjson.JSON;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.text.ParseException;

/**
 * Created by zqq on 2016/7/21.
 */
public class test {
    static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>();
    static {
        com.sun.org.apache.xml.internal.security.Init.init();
    }
    public static void main(String[] args) throws ParseException, InterruptedException, NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException {
        // 得到密钥对生成器
        KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);

        // 得到密钥对
        KeyPair kp=kpg.generateKeyPair();

        // 得到公钥
        RSAPublicKey keyPublic=(RSAPublicKey)kp.getPublic();
        String pub = JSON.toJSONString(keyPublic.getEncoded());
        System.out.println("公="+ pub);

        // 得到私钥
        RSAPrivateKey keyPrivate=(RSAPrivateKey)kp.getPrivate();
        String pri = JSON.toJSONString(keyPrivate.getEncoded());
        System.out.println("私="+pri);

    }

}
