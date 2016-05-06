/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model.moduleDES;

import com.computersecurity.hybridcryptography.model.moduleDES.DESBaseECB;
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
public class DESBaseECBTest {

    private final String path = "src/test/resources/";

    @Test
    public void testSecretKeyForEncryptionAndDecryption() {
        DESBaseECB ecb = new DESBaseECB();

        byte[] plaintext = "This is just an example".getBytes();
        byte[] ciphertext = ecb.getCipherText(plaintext, ecb.getDESKey());
        byte[] recovered = ecb.getPlainText(ciphertext, ecb.getDESKey());

        boolean expected = true;
        boolean result = Arrays.equals(plaintext, recovered);
        assertEquals("Plain text is same as recovered text", expected, result);

    }

    @Test
    public void testEncryptionAndDecryptionForImagePath() throws IOException {
        DESBaseECB ecb = new DESBaseECB();

        File file = new File(path + "images/palmTree.png");
        byte[] fileBytePath = Files.readAllBytes(file.toPath());
        byte[] cipherText = ecb.getCipherText(fileBytePath, ecb.getDESKey());
        byte[] recovered = ecb.getPlainText(cipherText, ecb.getDESKey());

        boolean expected = true;
        boolean result = Arrays.equals(fileBytePath, recovered);
        assertEquals("File Byte Path is same as recovered path", expected, result);
    }

    @Test
    public void testImageEncryptionAndDecryption() throws Exception {
        DESBaseECB ecb = new DESBaseECB(16);

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/testoutput/cipherPalmTree_DES_ECB.png");
        File recoveredFile = new File(path + "images/testoutput/recovPalmTree_DES_ECB.png");

        boolean expected = true;
        boolean isEncrypted = ecb.encryptImageFile(origFile, encryptedFile, ecb.getDESKey());
        boolean isDecrypted = ecb.decryptImageFile(encryptedFile, recoveredFile, ecb.getDESKey());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }

}
