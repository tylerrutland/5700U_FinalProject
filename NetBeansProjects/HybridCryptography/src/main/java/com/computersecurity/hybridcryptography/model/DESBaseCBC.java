/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

/**
 *
 * @author sm6668
 */
public class DESBaseCBC extends DESBase {

    private static final String CIPHER = "DES/CBC/PKCS5Padding";
    private Cipher cipher;

    public DESBaseCBC() {
        try {
            cipher = Cipher.getInstance(CIPHER);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException ex) {

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

            byte[] encodedParams = cipher.getParameters().getEncoded();
            AlgorithmParameters params = AlgorithmParameters.getInstance("DES");
            params.init(encodedParams);
            
            cipher.init(Cipher.DECRYPT_MODE, key, params);
            return cipher.doFinal(ciphertext);

        } catch (IOException 
                | NoSuchAlgorithmException |
                InvalidKeyException |
                InvalidAlgorithmParameterException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return null;

        }
    }
}
