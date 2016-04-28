/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleVEA;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;

/**
 * This class gets the key agreements between the two parties and then generates
 * a secret key using a Blowfish algorithm which takes a variable-length key, 
 * from 32 bits to 448 bits
 */
public class VEABase extends DHKeyAgreement2 {

    private static final String ALGORITHM = "Blowfish";
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

}
