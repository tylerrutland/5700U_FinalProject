/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Steve
 */
public class DHKeyAgreement2Test {

    @Test
    public void testKeyAgreement() {
        DHKeyAgreement2 dh = new DHKeyAgreement2(512);
        boolean expected = true;
        boolean result = dh.getSecretKeyA().equals(dh.getSecretKeyB());
        assertEquals("Same Key! ", expected, result);
    }

}
