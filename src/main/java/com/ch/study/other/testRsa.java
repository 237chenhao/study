package com.ch.study.other;

import java.security.*;

/**
 * Created by chenhao on 2016/10/25.
 */
public class testRsa {

    public static void main(String[] args) {
        String messageString = "text";
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA", "BC");
            keyGen.initialize(512, new SecureRandom());
            KeyPair keyPair = keyGen.generateKeyPair();
            PublicKey RSAPublicKey = keyPair.getPublic();
            PrivateKey RSAPrivateKey = keyPair.getPrivate();

            System.out.println("public key = " + RSAPublicKey);
            System.out.println("private key = " + RSAPrivateKey);


            Signature signature = Signature.getInstance("SHA1withRSA", "BC");
            signature.initSign(RSAPrivateKey, new SecureRandom());
            byte[] message = messageString.getBytes();
            signature.update(message);
            byte[] sigBytes = signature.sign();

            Signature signature1 = Signature.getInstance("SHA1withRSA", "BC");
            signature1.initVerify(RSAPublicKey);
            signature1.update(sigBytes);

            boolean result = signature1.verify(sigBytes);
            System.out.println("result = "+result);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | SignatureException | InvalidKeyException ex) {
            ex.printStackTrace();
        }
    }

}
