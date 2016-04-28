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
import javax.crypto.SecretKey;

/**
 * This class gets the key agreements between the two parties and then generates
 * a secret key using a DES algorithm which has a key length of 56 bits
 */
public class DESBase extends DHKeyAgreement2 {

    private static final String ALGORITHM = "DES";
    private SecretKey keyA, keyB;

    public DESBase() {
        try {
            /*
             Sets public key information
             */
            super.getSecretKeyA();
            super.getSecretKeyB();

            KeyAgreement kab = super.getKeyAgreementB();
            kab.doPhase(super.getPublicKeyA(), true);
            keyB = kab.generateSecret(ALGORITHM);

            KeyAgreement kaa = super.getKeyAgreementA();
            kaa.doPhase(super.getPublicKeyB(), true);
            keyA = kaa.generateSecret(ALGORITHM);

        } catch (InvalidKeyException |
                IllegalStateException |
                NoSuchAlgorithmException ex) {

            System.out.println(ex);

        }

    }

    public SecretKey getDESKeyA() {
        return keyA;
    }

    public SecretKey getDESKeyB() {
        return keyB;
    }

}
