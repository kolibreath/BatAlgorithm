package functions;

public abstract class IFunctions {
    private int index = 0;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }


    public abstract double func(double x[]);

}