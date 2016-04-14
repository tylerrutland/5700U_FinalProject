/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

/**
 *
 * @author sm6668
 */
public class DESBase extends DHKeyAgreement2 {

    private static final String ALGORITHM = "DES";
    private SecretKey keyDESA, keyDESB;

    public DESBase() {
        try {
            //Sets public key info.
            super.getSecretKeyA();
            super.getSecretKeyB();

            KeyAgreement kab = super.getKeyAgreementB();
            kab.doPhase(super.getPublicKeyA(), true);
            keyDESB = kab.generateSecret(ALGORITHM);

            KeyAgreement kaa = super.getKeyAgreementA();
            kaa.doPhase(super.getPublicKeyB(), true);
            keyDESA = kaa.generateSecret(ALGORITHM);

        } catch (InvalidKeyException |
                IllegalStateException |
                NoSuchAlgorithmException ex) {

            System.out.println(ex);

        }

    }

    public SecretKey getDESKeyA() {
        return keyDESA;
    }

    public SecretKey getDESKeyB() {
        return keyDESB;
    }

}
