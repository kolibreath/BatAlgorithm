package cn.edu.ccnu.kolibreath.al_viewer.model;

public class Particle {
    private boolean isElite;
    private double coodinate[];

    public boolean isElite() {
        return isElite;
    }

    public void setElite(boolean elite) {
        isElite = elite;
    }

    public double[] getCoodinate() {
        return coodinate;
    }

    public void setCoodinate(double[] coodinate) {
        this.coodinate = coodinate;
    }
}
