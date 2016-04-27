/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.VEABaseCBC;
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
public class VEABaseCBCTest {

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
        VEABaseCBC cbc = new VEABaseCBC();

        byte[] plaintext = "This is just an example".getBytes();
        byte[] ciphertext = cbc.getCipherText(plaintext, cbc.getVEAKeyA());
        byte[] recovered = cbc.getPlainText(ciphertext, cbc.getVEAKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(plaintext, recovered);
        assertEquals("Plain text is the same as recovered text", expected, result);
    }

    @Test
    public void testEncryptionAndDecryptionForImagePath() throws IOException {
        VEABaseCBC cbc = new VEABaseCBC();

        File file = new File(path + "images/palmTree.png");
        byte[] fileBytePath = Files.readAllBytes(file.toPath());
        byte[] cipherText = cbc.getCipherText(fileBytePath, cbc.getVEAKeyA());
        byte[] recovered = cbc.getPlainText(cipherText, cbc.getVEAKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(fileBytePath, recovered);
        assertEquals("File Byte Path is same as recovered path", expected, result);
    }

    @Test
    public void testImageEncryptionAndDecryption() throws Exception {
        VEABaseCBC cbc = new VEABaseCBC();

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/cipherPalmTree.png");
        File recoveredFile = new File(path + "images/recovPalmTree.png");

        boolean expected = true;
        boolean isEncrypted = cbc.encryptImage(origFile, encryptedFile, cbc.getVEAKeyA());
        boolean isDecrypted = cbc.decryptImage(encryptedFile, recoveredFile, cbc.getVEAKeyB());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }
}
