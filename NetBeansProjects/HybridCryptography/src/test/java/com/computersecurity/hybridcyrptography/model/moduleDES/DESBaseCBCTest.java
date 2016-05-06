/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model.moduleDES;

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
        byte[] ciphertext = cbc.getCipherText(plaintext, cbc.getDESKey());
        byte[] recovered = cbc.getPlainText(ciphertext, cbc.getDESKey());

        boolean expected = true;
        boolean result = Arrays.equals(plaintext, recovered);
        assertEquals("Plain text is the same as recovered text", expected, result);
    }

    @Test
    public void testEncryptionAndDecryptionForImagePath() throws IOException {
        DESBaseCBC cbc = new DESBaseCBC();

        File file = new File(path + "images/palmTree.png");
        byte[] fileBytePath = Files.readAllBytes(file.toPath());
        byte[] ciphertext = cbc.getCipherText(fileBytePath, cbc.getDESKey());
        byte[] recovered = cbc.getPlainText(ciphertext, cbc.getDESKey());

        boolean expected = true;
        boolean result = Arrays.equals(fileBytePath, recovered);
        assertEquals("File Byte Path is same as recovered path", expected, result);
    }

    @Test
    public void testImageEncryptionAndDecryption() throws Exception {
        DESBaseCBC cbc = new DESBaseCBC();

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/testoutput/cipherPalmTree_DES_CBC.png");
        File recoveredFile = new File(path + "images/testoutput/recovPalmTree_DES_CBC.png");

        boolean expected = true;
        boolean isEncrypted = cbc.encryptImageFile(origFile, encryptedFile, cbc.getDESKey());
        boolean isDecrypted = cbc.decryptImageFile(encryptedFile, recoveredFile, cbc.getDESKey());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }
}
