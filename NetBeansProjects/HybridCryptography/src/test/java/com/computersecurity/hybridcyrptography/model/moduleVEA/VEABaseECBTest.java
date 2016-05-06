/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model.moduleVEA;

import com.computersecurity.hybridcryptography.model.moduleVEA.VEABaseECB;
import java.io.File;
import java.lang.reflect.Field;
import static org.junit.Assert.assertEquals;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class VEABaseECBTest {

    private final String path = "src/test/resources/";

    @Test
    public void testImageFileEncryptionAndDecryption() throws Exception {
        VEABaseECB ecb = new VEABaseECB();

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/testoutput/cipherPalmTree_VEA_ECB.png");
        File recoveredFile = new File(path + "images/testoutput/recovPalmTree_VEA_ECB.png");

        boolean expected = true;
        boolean isEncrypted = ecb.encryptImageFile(origFile, encryptedFile, ecb.getVEAKey());
        boolean isDecrypted = ecb.decryptImageFile(encryptedFile, recoveredFile, ecb.getVEAKey());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }

    @Test
    public void testImageEncryptionAndDecryption() throws Exception {
        VEABaseECB cbc = new VEABaseECB();

        File origFile = new File(path + "images/palmTree.png");
        File encryptedFile = new File(path + "images/testoutput/visualCipherPalmTree_VEA_ECB.png");
        File recoveredFile = new File(path + "images/testoutput/visualRecovPalmTree_VEA_ECB.png");

        boolean expected = true;
        boolean isEncrypted = cbc.renderImage(origFile, encryptedFile, cbc.getVEAKey());
        boolean isDecrypted = cbc.derenderImage(encryptedFile, recoveredFile, cbc.getVEAKey());

        boolean result = (isEncrypted && isDecrypted);
        assertEquals("Image encrypted and decrypted successfully! ", expected, result);
    }
}
