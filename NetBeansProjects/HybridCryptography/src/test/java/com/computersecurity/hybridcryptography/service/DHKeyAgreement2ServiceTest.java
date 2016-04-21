/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class DHKeyAgreement2ServiceTest {

    @Test
    public void testDHKeyAgreement2Service() {
        DHKeyAgreement2 dhk = new DHKeyAgreement2(512);
        DHKeyAgreement2Service dhkService = new DHKeyAgreement2Service(dhk);

        boolean result = dhkService.getSecretKeyA().equals(dhkService.getSecretKeyB());
        assertEquals("Shared Secret Keys are equal!", true, result);

        boolean result2 = !Arrays.equals(dhkService.getPublicKeyA().getEncoded(),
                dhkService.getPublicKeyB().getEncoded());
        assertEquals("Public Keys are not equal", true, result2);

        boolean result3 = !Arrays.equals(dhkService.getPrivateKeyA().getEncoded(),
                dhkService.getPrivateKeyB().getEncoded());
        assertEquals("Private Keys are not equal", true, result3);
    }

    @Test
    public void testNullPublicKeys() {
        DHKeyAgreement2 dhk = new DHKeyAgreement2(512);
        DHKeyAgreement2Service dhkService = new DHKeyAgreement2Service(dhk);

        PublicKey keyA = dhkService.getPublicKeyA();
        PublicKey keyB = dhkService.getPublicKeyB();

        assertNull("Public Key A is null", keyA);
        assertNull("Public Key B is null", keyB);
    }

    @Test
    public void testNullPrivateKeys() {
        DHKeyAgreement2 dhk = new DHKeyAgreement2(512);
        DHKeyAgreement2Service dhkService = new DHKeyAgreement2Service(dhk);

        PrivateKey keyA = dhkService.getPrivateKeyA();
        PrivateKey keyB = dhkService.getPrivateKeyB();

        assertNull("Private Key A is null", keyA);
        assertNull("Private Key B is null", keyB);
    }

    @Test
    public void testNonNullPublicKeys() {
        DHKeyAgreement2 dhk = new DHKeyAgreement2(512);
        DHKeyAgreement2Service dhkService = new DHKeyAgreement2Service(dhk);

        dhkService.getSecretKeyA();
        dhkService.getSecretKeyB();

        PublicKey keyA = dhkService.getPublicKeyA();
        PublicKey keyB = dhkService.getPublicKeyB();

        assertNotNull("Public Key A is not null", keyA);
        assertNotNull("Public Key B is not null", keyB);
    }

    @Test
    public void testNonNullPrivateKeys() {
        DHKeyAgreement2 dhk = new DHKeyAgreement2(512);
        DHKeyAgreement2Service dhkService = new DHKeyAgreement2Service(dhk);

        dhkService.getSecretKeyA();
        dhkService.getSecretKeyB();

        PrivateKey keyA = dhkService.getPrivateKeyA();
        PrivateKey keyB = dhkService.getPrivateKeyB();

        assertNotNull("Private Key A is not null", keyA);
        assertNotNull("Private Key B is not null", keyB);
    }
}
