/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.DESBaseCBC;
import com.computersecurity.hybridcryptography.model.DESBaseECB;
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
        DESBaseCBC desBase = new DESBaseCBC();

        byte[] plaintext = "This is just an example".getBytes();
        byte[] ciphertext = desBase.getCipherText(plaintext, desBase.getDESKeyA());
        byte[] recovered = desBase.getPlainText(ciphertext, desBase.getDESKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(plaintext, recovered);
        assertEquals("Plain text is the same as recovered text", expected, result);
    }

    @Test
    public void testEncryptionAndDecryptionForImagePath() throws IOException {
        DESBaseCBC desBase = new DESBaseCBC();

        File file = new File(path + "images/palmTree.bmp");
        byte[] fileBytePath = Files.readAllBytes(file.toPath());
        byte[] cipherText = desBase.getCipherText(fileBytePath, desBase.getDESKeyA());
        byte[] recovered = desBase.getPlainText(cipherText, desBase.getDESKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(fileBytePath, recovered);
        assertEquals("File Byte Path is same as recovered path", expected, result);
    }

    @Test
    public void testImageEncryptionAndDecryption() throws Exception {
        DESBaseCBC desBase = new DESBaseCBC();

        File origFile = new File(path + "images/palmTree.bmp");
        File encryptedFile = new File(path + "images/cipherPalmTree.bmp");
        File recoveredFile = new File(path + "images/recovPalmTree.bmp");

        boolean expected = true;

        boolean isEncrypted = desBase.encryptImage(origFile, encryptedFile, desBase.getDESKeyA());
        boolean isDecrypted = desBase.decryptImage(encryptedFile, recoveredFile, desBase.getDESKeyB());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }
}
