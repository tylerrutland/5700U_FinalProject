/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography;

import com.computersecurity.hybridcryptography.model.DESBaseDHKeyAgreement2;
import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class DESBaseDHKeyAgreement2Test {

    @Test
    public void testSecretKeyForEncryptionAndDecryption() {
        DESBaseDHKeyAgreement2 desBase = new DESBaseDHKeyAgreement2(new DHKeyAgreement2(512));
        byte[] plaintext = "This is just an example".getBytes();
        byte[] ciphertext = desBase.getCipherText(plaintext, desBase.getSecretKeyA());
        byte[] recovered = desBase.getPlainText(ciphertext, desBase.getSecretKeyB());
        
        boolean expected = true;
        boolean result = Arrays.equals(plaintext, recovered);
        assertEquals(expected, result);
        
    }

}
