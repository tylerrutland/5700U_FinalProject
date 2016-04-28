/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleDES;

import com.computersecurity.hybridcryptography.model.Cryptable;
import static com.computersecurity.hybridcryptography.util.CryptoUtils.write;
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
 * This class uses DES algorithm and a Cipher Block Chaining mode to encrypt and
 * decrypt a plaintext or image file the initialization vector is generated
 * using a pseudorandom number generator of the algorithm "SHA1PRNG"
 */
public class DESBaseCBC extends DESBase implements Cryptable {

    private static final String ALGORITHM = "DES/CBC/PKCS7Padding";
    private static final String PROVIDER = "BC";
    private Cipher cipher;
    private SecureRandom secureRand;
    private IvParameterSpec ivParamSpec;

    public DESBaseCBC() {
        try {

            Security.addProvider(new BouncyCastleProvider());
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

    @Override
    public boolean encryptImage(File imageFile, File outputFile, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key, ivParamSpec);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            return false;
        }
    }

    @Override
    public boolean decryptImage(File imageFile, File outputFile, SecretKey key) {

        try {

            cipher.init(Cipher.DECRYPT_MODE, key, ivParamSpec);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            return false;
        }

    }

    @Override
    public byte[] getCipherText(byte[] plaintext, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key, ivParamSpec);
            return cipher.doFinal(plaintext);

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return null;
        }
    }

    @Override
    public byte[] getPlainText(byte[] ciphertext, SecretKey key) {
        try {

            cipher.init(Cipher.DECRYPT_MODE, key, ivParamSpec);
            return cipher.doFinal(ciphertext);

        } catch (InvalidKeyException |
                InvalidAlgorithmParameterException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return null;

        }
    }

}
