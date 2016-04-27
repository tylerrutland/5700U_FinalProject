/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.security.AlgorithmParameterGenerator;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

/**
 *
 * @author sm6668
 */
public class VEABase extends DHKeyAgreement2 {

    private int size;
    private static final String ALGORITHM = "Blowfish";
    private DHParameterSpec dhParamSpec;
    private AlgorithmParameterGenerator paramGen;
    private SecretKey keyA, keyB;

    public VEABase() {

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

    public SecretKey getVEAKeyA() {
        return keyA;
    }

    public SecretKey getVEAKeyB() {
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
