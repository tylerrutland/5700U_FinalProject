/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Steve
 */
public class VEABaseECB extends VEABase implements Cryptable {

    private static final String ALGORITHM = "Blowfish";
    private static final String PROVIDER = "BC";
    private Cipher cipher;

    public VEABaseECB() {

        try {
            Security.addProvider(new BouncyCastleProvider());
            cipher = Cipher.getInstance(ALGORITHM, PROVIDER);

        } catch (NoSuchAlgorithmException |
                NoSuchProviderException |
                NoSuchPaddingException ex) {

            System.out.println(ex);

        }

    }

    @Override
    public boolean encryptImage(File imageFile, File outputFile, SecretKey key) {
        return false;
    }

    @Override
    public boolean decryptImage(File imageFile, File outputFile, SecretKey key) {
        return false;
    }

    @Override
    public byte[] getCipherText(byte[] plaintext, SecretKey key) {
        return null;
    }

    @Override
    public byte[] getPlainText(byte[] ciphertext, SecretKey key) {
        return null;
    }

}
