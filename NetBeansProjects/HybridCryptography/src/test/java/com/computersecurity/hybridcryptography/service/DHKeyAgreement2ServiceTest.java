/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class DHKeyAgreement2ServiceTest {

    private final DHKeyAgreement2 dhk = new DHKeyAgreement2(512);
    private final DHKeyAgreement2Service dhkService = new DHKeyAgreement2Service(dhk);

    @Test
    public void testDHKeyAgreement2Service() {
        boolean result = dhkService.getSecretKeyA().equals(dhkService.getSecretKeyB());
        assertEquals("Same key!", true, result);

        boolean result2 = !Arrays.equals(dhkService.getPublicKeyA(), dhkService.getPublicKeyB());
        assertEquals("Keys are not equal", true, result2);

        boolean result3 = !Arrays.equals(dhkService.getPrivateKeyA(), dhkService.getPrivateKeyB());
        assertEquals("Keys are not equal", true, result3);
    }

}
