/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleVEA;

import com.computersecurity.hybridcryptography.model.Cryptable;
import static com.computersecurity.hybridcryptography.util.CryptoUtils.write;
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
 *
 * @author Steve
 */
public class VEABaseECB extends VEABase implements Cryptable {

    private static final String ALGORITHM = "Blowfish/ECB/PKCS5Padding";
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
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            return false;
        }
    }

    @Override
    public boolean decryptImage(File imageFile, File outputFile, SecretKey key) {

        try {

            cipher.init(Cipher.DECRYPT_MODE, key);
            write(cipher, imageFile, outputFile);
            return true;

        } catch (InvalidKeyException |
                IOException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            return false;
        }

    }

    @Override
    public byte[] getCipherText(byte[] plaintext, SecretKey key) {
        try {

            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(plaintext);

        } catch (InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return null;
        }
    }

    @Override
    public byte[] getPlainText(byte[] ciphertext, SecretKey key) {
        try {

            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(ciphertext);

        } catch (InvalidKeyException |
                IllegalBlockSizeException |
                BadPaddingException ex) {

            System.out.println(ex);
            return null;

        }
    }

}
