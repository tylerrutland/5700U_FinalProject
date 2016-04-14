/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Steve
 */
public class DHKeyAgreement2Test {

    private final DHKeyAgreement2 dh = new DHKeyAgreement2(512);

    @Test
    public void testKeyAgreement() {
        boolean expected = true;
        boolean result = dh.getSecretKeyA().equals(dh.getSecretKeyB());
        assertEquals("Same key! ", expected, result);
    }

    @Test
    public void testNullKeys(){
        
    }
}
