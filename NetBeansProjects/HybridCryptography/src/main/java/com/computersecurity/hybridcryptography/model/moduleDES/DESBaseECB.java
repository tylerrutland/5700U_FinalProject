/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleDES;

import static com.computersecurity.hybridcryptography.util.CryptoUtils.write;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 * This class uses DES algorithm and an Electronic Code Book mode to encrypt and
 * decrypt a plaintext or image file
 */
public class DESBaseECB extends DESBase {

    private int rounds;
    private static final String ALGORITHM = "DES/ECB/PKCS5Padding";
    private Cipher cipher;

    public DESBaseECB() {
        try {
            rounds = 0;
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException ex) {

            System.out.println(ex);

        }
    }

    public DESBaseECB(int rounds) {
        try {
            this.rounds = rounds;
            cipher = Cipher.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException ex) {

            System.out.println(ex);

        }
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public boolean encryptImageFile(File imageFile, File outputFile, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
        }
    }

    public boolean decryptImageFile(File imageFile, File outputFile, SecretKey key) {

        try {

            cipher.init(Cipher.DECRYPT_MODE, key);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
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
