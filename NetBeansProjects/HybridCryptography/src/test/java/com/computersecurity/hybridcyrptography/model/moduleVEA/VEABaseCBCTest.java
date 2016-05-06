/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model.moduleVEA;

import com.computersecurity.hybridcryptography.model.moduleVEA.VEABaseCBC;
import java.io.File;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class VEABaseCBCTest {

    private final String path = "src/test/resources/";

    @Test
    public void testImageFileEncryptionAndDecryption() throws Exception {
        VEABaseCBC cbc = new VEABaseCBC();

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/testoutput/cipherPalmTree_VEA_CBC.png");
        File recoveredFile = new File(path + "images/testoutput/recovPalmTree_VEA_CBC.png");

        boolean expected = true;
        boolean isEncrypted = cbc.encryptImageFile(origFile, encryptedFile, cbc.getVEAKey());
        boolean isDecrypted = cbc.decryptImageFile(encryptedFile, recoveredFile, cbc.getVEAKey());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }

    @Test
    public void testImageEncryptionAndDecryption() throws Exception {
        VEABaseCBC cbc = new VEABaseCBC();

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/testoutput/visualCipherPalmTree_VEA_CBC.png");
        File recoveredFile = new File(path + "images/testoutput/visualRecovPalmTree_VEA_CBC.png");

        boolean expected = true;
        boolean isEncrypted = cbc.renderImage(origFile, encryptedFile, cbc.getVEAKey());
        boolean isDecrypted = cbc.derenderImage(encryptedFile, recoveredFile, cbc.getVEAKey());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }
}
