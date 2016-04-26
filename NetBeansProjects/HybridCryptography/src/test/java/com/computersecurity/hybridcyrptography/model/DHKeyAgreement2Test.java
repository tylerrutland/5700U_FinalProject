/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author Steve
 */
public class DHKeyAgreement2Test {

    @Test
    public void testNullKey() {
        DHKeyAgreement2 dh = new DHKeyAgreement2(512);
        String keyB = dh.getSecretKeyB();
        String keyA = dh.getSecretKeyA();
        assertNull("KeyB is null before call to KeyA! ", keyB);
        assertNotNull("KeyA is not null! ", keyA);
    }

    @Test
    public void testNonNullKeys() {
        DHKeyAgreement2 dh = new DHKeyAgreement2(512);
        String keyA = dh.getSecretKeyA();
        String keyB = dh.getSecretKeyB();
        assertNotNull("Key A is not null! ", keyA);
        assertNotNull("Key B is not null! ", keyB);
    }

    @Test
    public void testKeyAgreement() {
        DHKeyAgreement2 dh = new DHKeyAgreement2(512);
        boolean expected = true;
        boolean result = dh.getSecretKeyA().equals(dh.getSecretKeyB());
        assertEquals("Same key! ", expected, result);
    }
}
