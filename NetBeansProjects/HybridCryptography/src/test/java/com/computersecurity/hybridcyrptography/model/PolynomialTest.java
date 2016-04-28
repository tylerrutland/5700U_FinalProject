/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.moduleVEA.Polynomial;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class PolynomialTest {

    @Test
    public void testZero() {
        Polynomial zero = new Polynomial(0, 0);

        String expected = "0";
        String result = zero.toString();

        assertEquals("Is zero ", expected, result);

    }

    @Test
    public void testPolynomial() {
        Polynomial p1 = new Polynomial(4, 3);

        String expected = "4x^3";
        String result = p1.toString();

        assertEquals("Is Correct ", expected, result);

    }

    @Test
    public void testAddition() {
        Polynomial q1 = new Polynomial(3, 2);
        Polynomial q2 = new Polynomial(5, 0);

        String expected = "3x^2 + 5";
        String result = q1.plus(q2).toString();

        assertEquals("Is Correct ", expected, result);

    }

    @Test
    public void testMultiplication() {
        Polynomial p1 = new Polynomial(4, 3);
        Polynomial p2 = new Polynomial(3, 2);
        Polynomial p3 = new Polynomial(1, 0);
        Polynomial p4 = new Polynomial(2, 1);
        Polynomial p = p1.plus(p2).plus(p3).plus(p4);

        Polynomial q1 = new Polynomial(3, 2);
        Polynomial q2 = new Polynomial(5, 0);
        Polynomial q = q1.plus(q2);

        String expected = "12x^5 + 9x^4 + 26x^3 + 18x^2 + 10x + 5";
        String result = p.times(q).toString();

        assertEquals("Is Correct ", expected, result);

    }
}
