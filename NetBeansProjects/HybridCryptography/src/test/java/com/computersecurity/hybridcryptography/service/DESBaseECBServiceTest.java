/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.DESBaseECB;
import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class DESBaseECBServiceTest {

    private final String path = "src/test/resources/";

    @Test
    public void testDESBaseECBServiceEncryptionAndDecryption() {
        DESBaseECB ecb = new DESBaseECB();
        DESBaseECBService ecbService = new DESBaseECBService(ecb);

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/cipherPalmTree.png");
        File recoveredFile = new File(path + "images/recovPalmTree.png");

        boolean expected = true;

        boolean isEncrypted = ecbService.encryptImage(origFile, encryptedFile);
        boolean isDecrypted = ecbService.decryptImage(encryptedFile, recoveredFile);

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);

    }

}
