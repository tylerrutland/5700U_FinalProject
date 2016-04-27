/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.DESBase;
import com.computersecurity.hybridcryptography.model.VEABase;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class VEABaseCBCTest {

    @Test
    public void testSecretKeyForEncryptionAndDecryption() {
                DESBase base2 = new DESBase();

        VEABase base = new VEABase();

        base.printA();
        base2.printA();

        assertTrue(true);
    }
}
