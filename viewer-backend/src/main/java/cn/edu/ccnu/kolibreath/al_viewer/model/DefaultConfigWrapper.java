package cn.edu.ccnu.kolibreath.al_viewer.model;

import cn.edu.ccnu.kolibreath.al_viewer.algorithm.functions.ImplementedFunctions;

public class DefaultConfigWrapper {
    private double lower[] = {-10.0, -10.0, -10.0};
    private double upper[] = {10.0,10.0,10.0};
    private int n = 20;//population size
    private int Ngen = 1000; // number of generation
    private double  A = 0.45; // loudness
    private double r =0.5;
    private double Qmin = 0.0;
    private double Qmax = 2.0;
    private ImplementedFunctions functions = new ImplementedFunctions();
    private double speed ;

    public double[] getLower() {
        return lower;
    }

    public void setLower(double[] lower) {
        this.lower = lower;
    }

    public double[] getUpper() {
        return upper;
    }

    public void setUpper(double[] upper) {
        this.upper = upper;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getNgen() {
        return Ngen;
    }

    public void setNgen(int ngen) {
        Ngen = ngen;
    }

    public double getA() {
        return A;
    }

    public void setA(double a) {
        A = a;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getQmin() {
        return Qmin;
    }

    public void setQmin(double qmin) {
        Qmin = qmin;
    }

    public double getQmax() {
        return Qmax;
    }

    public void setQmax(double qmax) {
        Qmax = qmax;
    }

    public ImplementedFunctions getFunctions() {
        return functions;
    }

    public void setFunctions(ImplementedFunctions functions) {
        this.functions = functions;
    }

    public void setSpeed(double speed){
        this.speed = speed;
    }

    public double getSpeed(){
        return speed;
    }

    public DefaultConfigWrapper(){}

    public DefaultConfigWrapper(double[] lower, double[] upper, int n, int ngen, double a, double r, double qmin, double qmax, ImplementedFunctions functions, double speed) {
        this.lower = lower;
        this.upper = upper;
        this.n = n;
        Ngen = ngen;
        A = a;
        this.r = r;
        Qmin = qmin;
        Qmax = qmax;
        this.functions = functions;
        this.speed = speed;
    }
}
