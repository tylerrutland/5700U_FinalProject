/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleVEA;

import static com.computersecurity.hybridcryptography.util.CryptoUtils.renderToFile;
import static com.computersecurity.hybridcryptography.util.CryptoUtils.writeToFile;
import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * This class uses Blowfish algorithm and a Cipher Block Chaining mode to
 * encrypt and decrypt a plaintext or image file the initialization vector is
 * generated using a pseudorandom number generator of the algorithm "SHA1PRNG"
 */
public class VEABaseCBC extends VEABase {

    private int rounds;
    private static final String ALGORITHM = "Blowfish/CBC/PKCS5Padding";
    private static final String PROVIDER = "BC";
    private Cipher cipher;
    private SecureRandom secureRand;
    private IvParameterSpec ivParamSpec;

    public VEABaseCBC() {
        try {
            Security.addProvider(new BouncyCastleProvider());
            rounds = 16;
            cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
            secureRand = SecureRandom.getInstance("SHA1PRNG");
            secureRand.nextBytes(new byte[cipher.getBlockSize()]);
            ivParamSpec = new IvParameterSpec(new byte[cipher.getBlockSize()]);

        } catch (NoSuchAlgorithmException |
                NoSuchProviderException |
                NoSuchPaddingException ex) {

            System.out.println(ex);

        }

    }

    public VEABaseCBC(int rounds) {
        try {
            Security.addProvider(new BouncyCastleProvider());
            this.rounds = rounds;
            cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
            secureRand = SecureRandom.getInstance("SHA1PRNG");
            secureRand.nextBytes(new byte[cipher.getBlockSize()]);
            ivParamSpec = new IvParameterSpec(new byte[cipher.getBlockSize()]);

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

    public boolean renderImage(File imageFile, File outputFile, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key, ivParamSpec);
            return renderToFile(cipher, rounds, imageFile, outputFile);

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
        }
    }

    public boolean derenderImage(File imageFile, File outputFile, SecretKey key) {
        try {

            cipher.init(Cipher.DECRYPT_MODE, key, ivParamSpec);
            return renderToFile(cipher, rounds, imageFile, outputFile);

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
        }
    }

    public boolean encryptImageFile(File imageFile, File outputFile, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key, ivParamSpec);
            writeToFile(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
        }
    }

    public boolean decryptImageFile(File imageFile, File outputFile, SecretKey key) {

        try {

            cipher.init(Cipher.DECRYPT_MODE, key, ivParamSpec);
            writeToFile(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return false;
        }

    }

}
