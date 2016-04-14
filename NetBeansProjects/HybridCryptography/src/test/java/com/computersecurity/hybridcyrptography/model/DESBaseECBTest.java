/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.DESBaseECB;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class DESBaseECBTest {

    @Test
    public void testSecretKeyForEncryptionAndDecryption() {
        DESBaseECB desBase = new DESBaseECB();
        
        byte[] plaintext = "This is just an example".getBytes();
        byte[] ciphertext = desBase.getCipherText(plaintext, desBase.getDESKeyA());
        byte[] recovered = desBase.getPlainText(ciphertext, desBase.getDESKeyB());

        boolean expected = true;
        boolean result = Arrays.equals(plaintext, recovered);
        assertEquals("Plain text as same as recovered text", expected, result);

    }

}
