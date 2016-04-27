/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.security.AlgorithmParameterGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;

/**
 *
 * @author sm6668
 */
public class VEABase extends DHKeyAgreement2 {

    private int size;
    private static final String ALGORITHM = "Blowfish";
    private DHParameterSpec dhParamSpec;
    private AlgorithmParameterGenerator paramGen;
    private SecretKey keyA, keyB;

    public VEABase() {

    }

}
