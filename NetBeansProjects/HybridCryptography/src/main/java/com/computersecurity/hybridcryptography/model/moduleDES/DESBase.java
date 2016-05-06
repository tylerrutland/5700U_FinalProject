/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleDES;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * This class gets the key agreements between the two parties and then generates
 * a secret key using a DES algorithm which has a key length of 56 bits
 */
public class DESBase {

    private int keySize;
    private static final String ALGORITHM = "DES";
    private SecretKey key;

    public DESBase() {
        try {

            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
            kg.init(keySize = 56);
            key = kg.generateKey();

        } catch (Exception ex) {

            System.out.println(ex);

        }

    }

    public int getKeySize() {
        return keySize;
    }

    public SecretKey getDESKey() {
        return key;
    }

}
