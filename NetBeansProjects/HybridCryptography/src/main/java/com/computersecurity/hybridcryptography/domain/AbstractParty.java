/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.domain;

import java.security.KeyPair;
import javax.crypto.KeyAgreement;

/**
 *
 * @author sm6668
 */
public interface AbstractParty {

    public KeyAgreement getKeyAgreementA();

    public KeyPair getKeyPairA();

    public KeyAgreement getKeyAgreementB();

    public KeyPair getKeyPairB();

}
