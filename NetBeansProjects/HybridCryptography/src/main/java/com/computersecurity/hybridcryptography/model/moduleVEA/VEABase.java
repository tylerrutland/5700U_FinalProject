/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleVEA;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * This class gets the key agreements between the two parties and then generates
 * a secret key using a Blowfish algorithm which takes a variable-length key,
 * from 32 bits to 448 bits
 */
public class VEABase {

    private int keySize;
    private static final String ALGORITHM = "Blowfish";
    private SecretKey key;

    public VEABase() {

        try {

            key = KeyGenerator.getInstance(ALGORITHM).generateKey();

        } catch (Exception ex) {

            System.out.println(ex);

        }

    }

    public VEABase(int size) {

        try {

            KeyGenerator kg = KeyGenerator.getInstance(ALGORITHM);
            kg.init(keySize = size);
            key = kg.generateKey();

        } catch (Exception ex) {

            System.out.println(ex);

        }

    }

    public int getKeySize() {
        return keySize;
    }

    public void setKeySize(int size) {
        keySize = size;
    }

    public SecretKey getVEAKey() {
        return key;
    }

}
