/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computersecurity.hybridcryptography.model.moduleVEA;

/**
 *
 * @author sm6668
 */
public class Term {

    private int coef;
    private int deg;
    private int x;

    public Term() {

    }

    public Term(int coef, int deg, int x) {
        this.coef = coef;
        this.deg = deg;
        this.x = x;
    }

    public int getCoefficient() {
        return coef;
    }

    public void setCoefficient(int coef) {
        this.coef = coef;
    }

    public int getDegree() {
        return deg;
    }

    public void setDegree(int deg) {
        this.deg = deg;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getTermValue() {
        return (int) (coef * Math.pow(x, deg));
    }

    @Override
    public String toString() {
        return "Coefficient: " + coef + "\n"
                + "Degree: " + deg + "\n"
                + "X: " + x + "\n"
                + "Value: " + getTermValue();
    }

}
