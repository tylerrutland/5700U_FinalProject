/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model.moduleVEA;

import com.computersecurity.hybridcryptography.model.moduleVEA.Term;
import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class TermTest {

    @Test
    public void testTerm() {
        BigInteger coef = new BigInteger("10");
        BigInteger deg = new BigInteger("2");
        BigInteger x = new BigInteger("4");

        Term term = new Term(coef, deg, x);

        int expected = 160;
        int result = term.getTermValue().intValue();

        assertEquals("Is Correct ", expected, result);
    }

    @Test
    public void testTerm2() {
        BigInteger coef = new BigInteger("0");
        BigInteger deg = new BigInteger("2");
        BigInteger x = new BigInteger("4");

        Term term = new Term(coef, deg, x);

        int expected = 0;
        int result = term.getTermValue().intValue();

        assertEquals("Is Correct ", expected, result);
    }

    @Test
    public void testTerm3() {
        BigInteger coef = new BigInteger("5");
        BigInteger deg = new BigInteger("0");
        BigInteger x = new BigInteger("10");

        Term term = new Term(coef, deg, x);

        int expected = 5;
        int result = term.getTermValue().intValue();

        assertEquals("Is Correct ", expected, result);
    }
}
