/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author sm6668
 */
public class DESBaseDHKeyAgreement2 {

    private DHKeyAgreement2 dh;
    private Cipher cipher;
    private SecretKey secretKeyA, secretKeyB;

    public DESBaseDHKeyAgreement2(DHKeyAgreement2 dhk) {
        try {
            dh = dhk;
            dh.getSecretKeyA();//Sets public key info.
            dh.getSecretKeyB();//Sets public key info.
            KeyAgreement kab = dh.getKeyAgreementB();
            kab.doPhase(dh.getPublicKeyA(), true);
            secretKeyB = kab.generateSecret("DES");

            KeyAgreement kaa = dh.getKeyAgreementA();
            kaa.doPhase(dh.getPublicKeyB(), true);
            secretKeyA = kaa.generateSecret("DES");
            cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        } catch (InvalidKeyException |
                IllegalStateException |
                NoSuchAlgorithmException |
                NoSuchPaddingException ex) {

            System.out.println(ex);

        }
    }

    public SecretKey getSecretKeyA() {
        return secretKeyA;
    }

    public SecretKey getSecretKeyB() {
        return secretKeyB;
    }

    public byte[] getCipherText(byte[] plaintext, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plaintext);

        } catch (InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return null;
        }
    }

    public byte[] getPlainText(byte[] ciphertext, SecretKey key) {
        try {

            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(ciphertext);

        } catch (InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return null;

        }
    }

}
