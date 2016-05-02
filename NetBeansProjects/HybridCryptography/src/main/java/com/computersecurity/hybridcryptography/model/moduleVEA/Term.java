/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleVEA;

import java.math.BigInteger;

/**
 *
 * @author sm6668
 */
public class Term {

    private BigInteger coef;
    private BigInteger deg;
    private BigInteger x;

    public Term() {

    }

    public Term(BigInteger coef, BigInteger deg, BigInteger x) {
        this.coef = coef;
        this.deg = deg;
        this.x = x;
    }

    public BigInteger getCoefficient() {
        return coef;
    }

    public void setCoefficient(BigInteger coef) {
        this.coef = coef;
    }

    public BigInteger getDegree() {
        return deg;
    }

    public void setDegree(BigInteger deg) {
        this.deg = deg;
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getTermValue() {
        for (int i = 0; i < deg.intValue(); i++) {
            x = x.multiply(x);
        }
        
        return x.multiply(coef);
    }

    @Override
    public String toString() {
        return "Coefficient: " + coef.intValue() + "\n"
                + "Degree: " + deg.intValue() + "\n"
                + "X: " + x.intValue() + "\n"
                + "Value: " + getTermValue().intValue();
    }

}
