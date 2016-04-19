/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

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
public class DESBaseECBTest {

    private final String path = "src/test/resources/";

    @Test
    public void testSecretKeyForEncryptionAndDecryption() {
        DESBaseECB desBase = new DESBaseECB();

        byte[] plaintext = "This is just an example".getBytes();
        byte[] ciphertext = desBase.getCipherText(plaintext, desBase.getDESKeyA());
        byte[] recovered = desBase.getPlainText(ciphertext, desBase.getDESKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(plaintext, recovered);
        assertEquals("Plain text is same as recovered text", expected, result);

    }

    @Test
    public void testEncryptionAndDecryptionForImage() throws IOException {
        DESBaseECB desBase = new DESBaseECB();

        File file = new File(path + "images/palmTree.bmp");
        byte[] fileBytePath = Files.readAllBytes(file.toPath());
        byte[] cipherText = desBase.getCipherText(fileBytePath, desBase.getDESKeyA());
        byte[] recovered = desBase.getPlainText(cipherText, desBase.getDESKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(fileBytePath, recovered);
        assertEquals("File Byte Path is same as recovered path", expected, result);
    }

}
