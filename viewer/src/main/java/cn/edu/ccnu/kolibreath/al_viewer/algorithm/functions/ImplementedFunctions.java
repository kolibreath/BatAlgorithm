package cn.edu.ccnu.kolibreath.al_viewer.algorithm.functions;

public class ImplementedFunctions extends IFunctions {
    @Override
    public double func(double[] x) {
        Functions functions = new Functions();
        return functions.runAll(x,x.length,getIndex());
    }
}
