/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author sm6668
 */
public class RSA {

    private final Random r;
    private final BigInteger p, q, n, z, e, d;
    private int size;

    public RSA() {
        size = 512;
        r = new SecureRandom();
        p = BigInteger.probablePrime(size, r);
        q = BigInteger.probablePrime(size, r);
        n = p.multiply(q);

        z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(size / 2, r);

        while (z.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(z) < 0) {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(z);
    }

    public RSA(int size) {
        this.size = size;
        r = new SecureRandom();
        p = BigInteger.probablePrime(size, r);
        q = BigInteger.probablePrime(size, r);
        n = p.multiply(q);

        z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(size / 2, r);

        while (z.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(z) < 0) {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(z);
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getZ() {
        return z;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

    //Encrypt message
    public byte[] encrypt(byte[] message) {
        return (new BigInteger(message)).modPow(e, n).toByteArray();
    }

    // Decrypt message
    public byte[] decrypt(byte[] message) {
        return (new BigInteger(message)).modPow(d, n).toByteArray();
    }

    public static String bytesToString(byte[] encrypted) {
        String test = "";
        for (byte b : encrypted) {
            test += Byte.toString(b);
        }
        return test;
    }

}
