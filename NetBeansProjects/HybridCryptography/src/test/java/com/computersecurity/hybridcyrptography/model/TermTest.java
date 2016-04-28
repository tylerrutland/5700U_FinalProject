/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcyrptography.model;

import com.computersecurity.hybridcryptography.model.moduleVEA.Term;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author sm6668
 */
public class TermTest {

    @Test
    public void testTerm() {
        Term term = new Term(10, 2, 4);

        int expected = 160;
        int result = term.getTermValue();

        assertEquals("Is Correct ", expected, result);
    }

}
