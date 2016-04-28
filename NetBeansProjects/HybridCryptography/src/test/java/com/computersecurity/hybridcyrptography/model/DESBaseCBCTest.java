/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseCBC;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class DESBaseCBCTest {

    private final String path = "src/test/resources/";

    @Test
    public void testSecretKeyForEncryptionAndDecryption() {
        DESBaseCBC cbc = new DESBaseCBC();

        byte[] plaintext = "This is just an example".getBytes();
        byte[] ciphertext = cbc.getCipherText(plaintext, cbc.getDESKeyA());
        byte[] recovered = cbc.getPlainText(ciphertext, cbc.getDESKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(plaintext, recovered);
        assertEquals("Plain text is the same as recovered text", expected, result);
    }

    @Test
    public void testEncryptionAndDecryptionForImagePath() throws IOException {
        DESBaseCBC cbc = new DESBaseCBC();

        File file = new File(path + "images/palmTree.png");
        byte[] fileBytePath = Files.readAllBytes(file.toPath());
        byte[] cipherText = cbc.getCipherText(fileBytePath, cbc.getDESKeyA());
        byte[] recovered = cbc.getPlainText(cipherText, cbc.getDESKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(fileBytePath, recovered);
        assertEquals("File Byte Path is same as recovered path", expected, result);
    }

    @Test
    public void testImageEncryptionAndDecryption() throws Exception {
        DESBaseCBC cbc = new DESBaseCBC();

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/cipherPalmTree.png");
        File recoveredFile = new File(path + "images/recovPalmTree.png");

        boolean expected = true;
        boolean isEncrypted = cbc.encryptImage(origFile, encryptedFile, cbc.getDESKeyA());
        boolean isDecrypted = cbc.decryptImage(encryptedFile, recoveredFile, cbc.getDESKeyB());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }
}
