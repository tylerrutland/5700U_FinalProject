/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

/**
 *
 * @author sm6668
 */
public class DESBase extends DHKeyAgreement2{

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

    public void printA() {
        System.out.println(Arrays.toString(keyA.getEncoded()));
        System.out.println(keyA.getAlgorithm());
    }

    public void printB() {
        System.out.println(Arrays.toString(keyB.getEncoded()));
        System.out.println(keyB.getAlgorithm());
    }

}
