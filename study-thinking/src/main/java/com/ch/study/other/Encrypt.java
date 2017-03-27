package com.ch.study.other;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Created by zqq on 2016/7/21.
 */
public class Encrypt {
    public static void main(String[] args) throws Exception {
        Random random = new Random();
        //生成密匙
        //byte[] rawKeyData = generatorKey();
        String key = String.valueOf(System.currentTimeMillis()+random.nextInt(10000));
        System.out.println("加密Key：\t"+key);
        byte[] rawKeyData = key.getBytes("UTF-8");
        String str="加密前的字符串";
        byte[] strBytes = str.getBytes("UTF-8");
        //加密后的数据
        byte[] encryptedData = encrypt(rawKeyData,strBytes);

        //解密
        byte[] decryptedData = decrypt(rawKeyData,encryptedData);

        System.out.println(new String(decryptedData,"UTF-8"));
    }
    //生成密匙
    public static byte[] generatorKey() throws NoSuchAlgorithmException {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 为我们选择的DES算法生成一个KeyGenerator对象
        KeyGenerator kg = KeyGenerator.getInstance( "DES" );
        kg.init( sr );
        // 生成密匙
        SecretKey key = kg.generateKey();
        // 获取密匙数据
        byte[] rawKeyData = key.getEncoded();
        return rawKeyData;
    }

    //通过密匙加密数据
    public static byte[] encrypt(byte[] rawKeyData,byte[] data) throws Exception{
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        //byte rawKeyData[] = /* 用某种方法获得密匙数据 */;
        // 从原始密匙数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec( rawKeyData );
        // 创建一个密匙工厂，然后用它把DESKeySpec转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance( "DES" );
        SecretKey key = keyFactory.generateSecret( dks );
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance( "DES" );
        // 用密匙初始化Cipher对象
        cipher.init( Cipher.ENCRYPT_MODE, key, sr );
        // 现在，获取数据并加密
        //byte data[] = /* 用某种方法获取数据 */
        // 正式执行加密操作
        byte encryptedData[] = cipher.doFinal( data );
        // 进一步处理加密后的数据
        System.out.println(new String(encryptedData));
        return encryptedData;
    }
    //通过密匙解密数据
    public static byte[] decrypt(byte[] rawKeyData,byte[] data) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        //byte[] rawKeyData = /* 用某种方法获取原始密匙数据 */;
        // 从原始密匙数据创建一个DESKeySpec对象
        DESKeySpec dks = new DESKeySpec( rawKeyData );
        // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
        // 一个SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance( "DES" );
        SecretKey key = keyFactory.generateSecret( dks );
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance( "DES" );
        // 用密匙初始化Cipher对象
        cipher.init( Cipher.DECRYPT_MODE, key, sr );
        // 现在，获取数据并解密
        //byte encryptedData[] = /* 获得经过加密的数据 */
        // 正式执行解密操作
        byte decryptedData[] = cipher.doFinal( data );
        // 进一步处理解密后的数据
        return decryptedData;
    }
}
