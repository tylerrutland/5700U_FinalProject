/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
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

    public static void write(Cipher cipher, File imageFile, File outputFile)
            throws IOException, IllegalBlockSizeException, BadPaddingException {

        FileOutputStream fos;
        FileInputStream fis = new FileInputStream(imageFile);
        fos = new FileOutputStream(outputFile);
        byte[] buffer = new byte[4096];
        int len;
        while ((len = fis.read(buffer)) > 0) {
            fos.write(cipher.update(buffer, 0, len));
            fos.flush();
        }
        fos.write(cipher.doFinal());
        fos.close();

    }
}
