/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.security.AlgorithmParameterGenerator;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.KeyAgreement;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import static org.bouncycastle.pqc.math.linearalgebra.ByteUtils.toHexString;

/**
 *
 * @author Steve
 */
public class DHKeyAgreement2 {

    private static final String ALGORITHM = "DH";
    private DHParameterSpec dhParamSpec;
    private AlgorithmParameterGenerator paramGen;
    private KeyAgreement keyAgreeA, keyAgreeB;
    private PublicKey publicKeyA, publicKeyB;
    private PrivateKey privateKeyA, privateKeyB;
    private X509EncodedKeySpec x509KeySpec;

    public DHKeyAgreement2() {
        try {
            paramGen = AlgorithmParameterGenerator.getInstance(ALGORITHM);
            paramGen.init(512);
            dhParamSpec = (paramGen.generateParameters()).getParameterSpec(DHParameterSpec.class);
            keyAgreeA = KeyAgreement.getInstance(ALGORITHM);
            keyAgreeB = KeyAgreement.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | InvalidParameterSpecException ex) {
            System.out.println(ex);
        }
    }

    public DHKeyAgreement2(int size) {
        try {
            paramGen = AlgorithmParameterGenerator.getInstance(ALGORITHM);
            paramGen.init(size);
            dhParamSpec = (paramGen.generateParameters()).getParameterSpec(DHParameterSpec.class);
            keyAgreeA = KeyAgreement.getInstance(ALGORITHM);
            keyAgreeB = KeyAgreement.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException | InvalidParameterSpecException ex) {
            System.out.println(ex);
        }
    }

    public DHParameterSpec getDHParameterSpec() {
        return dhParamSpec;
    }

    public void setSize(int size) {
        paramGen.init(size);
    }

    public PublicKey getPublicKeyA() {
        return publicKeyA;

    }

    public PublicKey getPublicKeyB() {
        return publicKeyB;
    }

    public PrivateKey getPrivateKeyA() {
        return privateKeyA;
    }

    public PrivateKey getPrivateKeyB() {
        return privateKeyB;
    }

    public KeyAgreement getKeyAgreementA() throws InvalidKeyException {
        return keyAgreeA;
    }

    public KeyAgreement getKeyAgreementB() throws InvalidKeyException {
        return keyAgreeB;
    }

    public String getSecretKeyA() {
        try {
            KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
            x509KeySpec = new X509EncodedKeySpec(publicKeyEncodedB(
                    sendPublicKeyEncoded(publicKeyEncodedA())));
            publicKeyB = kf.generatePublic(x509KeySpec);
            keyAgreeA.doPhase(publicKeyB, true);
            return toHexString(keyAgreeA.generateSecret());
        } catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException |
                InvalidKeySpecException | InvalidKeyException ex) {
            return null;
        }
    }

    public String getSecretKeyB() {
        try {
            keyAgreeB.doPhase(publicKeyA, true);
            return toHexString(keyAgreeB.generateSecret());
        } catch (InvalidKeyException ex) {
            return null;
        }
    }

    private byte[] publicKeyEncodedA() throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        kpg.initialize(dhParamSpec);
        KeyPair kp = kpg.generateKeyPair();
        keyAgreeA.init(kp.getPrivate());
        privateKeyA = kp.getPrivate();
        return kp.getPublic().getEncoded();
    }

    private DHParameterSpec sendPublicKeyEncoded(byte[] pke) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance(ALGORITHM);
        x509KeySpec = new X509EncodedKeySpec(pke);
        publicKeyA = kf.generatePublic(x509KeySpec);
        return ((DHPublicKey) publicKeyA).getParams();
    }

    private byte[] publicKeyEncodedB(DHParameterSpec dhps) throws InvalidAlgorithmParameterException, InvalidKeyException, NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGORITHM);
        kpg.initialize(dhps);
        KeyPair kp = kpg.generateKeyPair();
        keyAgreeB.init(kp.getPrivate());
        privateKeyB = kp.getPrivate();
        return kp.getPublic().getEncoded();
    }

}
