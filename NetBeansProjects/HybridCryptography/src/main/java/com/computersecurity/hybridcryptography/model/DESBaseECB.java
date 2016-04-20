/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author sm6668
 */
public class DESBaseECB extends DESBase {

    private static final String ALGORITHM = "DES/ECB/PKCS5Padding";
    private Cipher cipher;

    public DESBaseECB() {
        try {
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException ex) {

            System.out.println(ex);

        }
    }

    public void encryptImage(File inputFile, File outputFile, SecretKey key) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);

            FileOutputStream fos;
            try (CipherInputStream cis = new CipherInputStream(
                    new FileInputStream(inputFile), cipher)) {
                fos = new FileOutputStream(outputFile);
                int i;
                while ((i = cis.read()) != -1) {
                    fos.write(i);
                }
            }
            fos.close();

        } catch (InvalidKeyException | IOException ex) {
            System.out.println(ex);
        }
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
