/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography;

import com.computersecurity.hybridcryptography.model.RSA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class RSATest {

    public final RSA rsa = new RSA(512);

    @Test
    public void testRSA() {
        String initialMessage = "This is a test string for encryption and decryption";
        byte[] encrypted = rsa.encrypt(initialMessage.getBytes());

        byte[] decrypted = rsa.decrypt(encrypted);
        String finalMessage = new String(decrypted);

        boolean expected = true;
        boolean result = initialMessage.equals(finalMessage);

        assertEquals("The final message is the same as the original message",
                expected, result);
    }

}
