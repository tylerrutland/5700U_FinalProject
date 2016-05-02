/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleVEA;

import static com.computersecurity.hybridcryptography.util.CryptoUtils.write;
import static com.computersecurity.hybridcryptography.util.CryptoUtils.writeImage;
import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class uses Blowfish algorithm and an Electronic Code Book mode to
 * encrypt and decrypt a plaintext or image file"
 */
public class VEABaseECB extends VEABase {

    private int rounds;
    private static final String ALGORITHM = "Blowfish/ECB/PKCS5Padding";
    private static final String PROVIDER = "BC";
    private Cipher cipher;

    public VEABaseECB() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            rounds = 16;
            cipher = Cipher.getInstance(ALGORITHM, PROVIDER);

        } catch (NoSuchAlgorithmException |
                NoSuchProviderException |
                NoSuchPaddingException ex) {

            System.out.println(ex);

        }

    }

    public VEABaseECB(int rounds) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            this.rounds = rounds;
            cipher = Cipher.getInstance(ALGORITHM, PROVIDER);

        } catch (NoSuchAlgorithmException |
                NoSuchProviderException |
                NoSuchPaddingException ex) {

            System.out.println(ex);

        }

    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public boolean encryptImage(File imageFile, File outputFile, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key);
            return writeImage(cipher, rounds, imageFile, outputFile);

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
        }
    }

    public boolean decryptImage(File imageFile, File outputFile, SecretKey key) {
        try {

            cipher.init(Cipher.DECRYPT_MODE, key);
            return writeImage(cipher, rounds, imageFile, outputFile);

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
        }
    }

    public boolean encryptImageFile(File imageFile, File outputFile, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
        }
    }

    public boolean decryptImageFile(File imageFile, File outputFile, SecretKey key) {

        try {

            cipher.init(Cipher.DECRYPT_MODE, key);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
        }

    }

}
