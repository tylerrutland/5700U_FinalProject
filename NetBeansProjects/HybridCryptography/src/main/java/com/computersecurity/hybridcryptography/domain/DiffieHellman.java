/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.domain;

import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author sm6668
 */
public class DiffieHellman implements AbstractParty {

    private static final String ALGORITHM = "DH";
    private static final String PROVIDER = "BC";
    private final DHParameterSpec dhParams;

    public DiffieHellman(BigInteger modulus, BigInteger base) throws NoSuchProviderException, NoSuchAlgorithmException {
        Security.addProvider(new BouncyCastleProvider());
        dhParams = new DHParameterSpec(modulus, base);
    }

    public BigInteger getBase() {
        return dhParams.getG();
    }

    public BigInteger getModulus() {
        return dhParams.getP();
    }

    @Override
    public KeyAgreement getKeyAgreementA() {
        return getKeyAgreement();
    }

    @Override
    public KeyPair getKeyPairA() {
        return getKeyPair();
    }

    @Override
    public KeyAgreement getKeyAgreementB() {
        return getKeyAgreement();
    }

    @Override
    public KeyPair getKeyPairB() {
        return getKeyPair();
    }

    private KeyPair getKeyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
            keyPairGen.initialize(512);
            keyPairGen.initialize(dhParams, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            return keyPair;

        } catch (NoSuchAlgorithmException |
                NoSuchProviderException |
                InvalidAlgorithmParameterException ex) {
            return null;
        }

    }

    private KeyAgreement getKeyAgreement() {
        try {
            KeyAgreement keyAgree = KeyAgreement.getInstance(ALGORITHM, PROVIDER);
            return keyAgree;
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            return null;
        }
    }
}
