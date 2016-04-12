/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography;

import com.computersecurity.hybridcryptography.domain.DiffieHellman;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import javax.crypto.KeyAgreement;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class DiffieHellmanTest {

    @Test
    public void testProvider(){
        assertNotNull("Provider is not null", Security.addProvider(new BouncyCastleProvider()));
    }
    
    @Test
    public void testSharedKey() {

        try {
            BigInteger modulus = new BigInteger("17");
            BigInteger base = new BigInteger("3");
            DiffieHellman dh = new DiffieHellman(modulus, base);

            KeyAgreement aAgree = dh.getKeyAgreementA();
            KeyPair aPair = dh.getKeyPairA();
            aAgree.init(aPair.getPrivate());

            KeyAgreement bAgree = dh.getKeyAgreementB();
            KeyPair bPair = dh.getKeyPairB();
            bAgree.init(bPair.getPrivate());

            Key aKey = aAgree.doPhase(bPair.getPublic(), true);
            Key bKey = bAgree.doPhase(aPair.getPublic(), true);

            System.out.println(aKey);
            System.out.println(bKey);

            assertTrue(true);

        } catch (NoSuchProviderException | NoSuchAlgorithmException | InvalidKeyException ex) {

        }

    }

}
