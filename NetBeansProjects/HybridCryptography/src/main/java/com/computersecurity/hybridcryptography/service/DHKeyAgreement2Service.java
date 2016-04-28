/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.service;

import com.computersecurity.hybridcryptography.model.DHKeyAgreement2;
import java.math.BigInteger;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * This class services the specified DHKeyAgreement2 object with the desired
 * methods for the presentation layer of the application
 */
public class DHKeyAgreement2Service {

    public DHKeyAgreement2 dhk;

    public DHKeyAgreement2Service(DHKeyAgreement2 dhk) {
        this.dhk = dhk;
    }

    public DHKeyAgreement2 getDHKeyAgreement2() {
        return dhk;
    }

    public void setDHKeyAgreement2(DHKeyAgreement2 dhk) {
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

    public PublicKey getPublicKeyA() {
        return dhk.getPublicKeyA();
    }

    public PublicKey getPublicKeyB() {
        return dhk.getPublicKeyB();
    }

    public PrivateKey getPrivateKeyA() {
        return dhk.getPrivateKeyA();
    }

    public PrivateKey getPrivateKeyB() {
        return dhk.getPrivateKeyB();
    }
}
