public class ImplementedFunctions extends IFunctions {
    @Override
    double func(double[] x) {
        Functions functions = new Functions();
        return functions.runAll(x,x.length,getIndex());
    }
}
