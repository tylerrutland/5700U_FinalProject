/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.DESBaseCBC;
import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class DESBaseCBCServiceTest {

    private final String path = "src/test/resources/";

    @Test
    public void testDESBaseCBCServiceEncryptionAndDecryption() {
        DESBaseCBC cbc = new DESBaseCBC();
        DESBaseCBCService cbcService = new DESBaseCBCService(cbc);

        File origFile = new File(path + "images/palmTree.bmp");
        File encryptedFile = new File(path + "images/cipherPalmTree.bmp");
        File recoveredFile = new File(path + "images/recovPalmTree.bmp");

        boolean expected = true;

        boolean isEncrypted = cbcService.encryptImage(origFile, encryptedFile);
        boolean isDecrypted = cbcService.decryptImage(encryptedFile, recoveredFile);

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);

    }
}
