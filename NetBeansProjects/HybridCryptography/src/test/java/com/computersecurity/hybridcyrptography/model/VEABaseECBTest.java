/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.VEABaseECB;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class VEABaseECBTest {

    private final String path = "src/test/resources/";

    /*
     For the Blowfish to encrypt the application needs 
     the this code in the setUpBeforeClass() method
     this is probably a code hack
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        try {
            Field field = Class.forName("javax.crypto.JceSecurity").getDeclaredField("isRestricted");
            field.setAccessible(true);
            field.set(null, java.lang.Boolean.FALSE);
        } catch (ClassNotFoundException |
                NoSuchFieldException |
                SecurityException |
                IllegalArgumentException |
                IllegalAccessException ex) {

            ex.printStackTrace(System.err);
        }
    }

    @Test
    public void testSecretKeyForEncryptionAndDecryption() {
        VEABaseECB ecb = new VEABaseECB();

        byte[] plaintext = "This is just an example".getBytes();
        byte[] ciphertext = ecb.getCipherText(plaintext, ecb.getVEAKeyA());
        byte[] recovered = ecb.getPlainText(ciphertext, ecb.getVEAKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(plaintext, recovered);
        assertEquals("Plain text is the same as recovered text", expected, result);
    }

    @Test
    public void testEncryptionAndDecryptionForImagePath() throws IOException {
        VEABaseECB ecb = new VEABaseECB();

        File file = new File(path + "images/palmTree.png");
        byte[] fileBytePath = Files.readAllBytes(file.toPath());
        byte[] cipherText = ecb.getCipherText(fileBytePath, ecb.getVEAKeyA());
        byte[] recovered = ecb.getPlainText(cipherText, ecb.getVEAKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(fileBytePath, recovered);
        assertEquals("File Byte Path is same as recovered path", expected, result);
    }

    @Test
    public void testImageEncryptionAndDecryption() throws Exception {
        VEABaseECB ecb = new VEABaseECB();

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/cipherPalmTree.png");
        File recoveredFile = new File(path + "images/recovPalmTree.png");

        boolean expected = true;
        boolean isEncrypted = ecb.encryptImage(origFile, encryptedFile, ecb.getVEAKeyA());
        boolean isDecrypted = ecb.decryptImage(encryptedFile, recoveredFile, ecb.getVEAKeyB());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }

}
