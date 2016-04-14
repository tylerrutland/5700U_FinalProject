/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import java.math.BigInteger;

/**
 *
 * @author sm6668
 */
public class DHKeyAgreement2Service {

    public DHKeyAgreement2 dhk;

    public DHKeyAgreement2Service(DHKeyAgreement2 dhk) {
        this.dhk = dhk;
    }

    public BigInteger getModulus() {
        return dhk.getDHParameterSpec().getP();
    }

    public BigInteger getBase() {
        return dhk.getDHParameterSpec().getG();
    }

    public String getSecretKeyA() {
        return dhk.getSecretKeyA();
    }

    public String getSecretKeyB() {
        return dhk.getSecretKeyB();
    }

    public byte[] getPublicKeyA() {
        return dhk.getPublicKeyA().getEncoded();
    }

    public byte[] getPublicKeyB() {
        return dhk.getPublicKeyB().getEncoded();
    }

    public byte[] getPrivateKeyA() {
        return dhk.getPrivateKeyA().getEncoded();
    }

    public byte[] getPrivateKeyB() {
        return dhk.getPrivateKeyB().getEncoded();
    }
}
