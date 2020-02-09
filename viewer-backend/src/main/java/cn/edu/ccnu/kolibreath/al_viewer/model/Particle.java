package cn.edu.ccnu.kolibreath.al_viewer.model;

public class Particle {
    private boolean isElite;
    private double coodinate[];
    private double x;
    private double y;
    private double z;

    public boolean isElite() {
        return isElite;
    }

    public void setElite(boolean elite) {
        isElite = elite;
    }

    public double[] getCoodinate() {
        return coodinate;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public void setCoodinate(double[] coodinate) {
        this.coodinate = coodinate;
       x = coodinate[0] ;
       y = coodinate[1] ;
       z = coodinate[2] ;
    }
}
