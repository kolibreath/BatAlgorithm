package cn.edu.ccnu.kolibreath.al_viewer.model;

public class IterationWrapper {
    private int iteration;

    private double improvedMin;
    private double originalMin;

    private double improvedAve;
    private double originalAve;

    private double improvedStd;
    private double originalStd;

    private double improvedMax;
    private double originalMax;

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public double getImprovedMin() {
        return improvedMin;
    }

    public void setImprovedMin(double improvedMin) {
        this.improvedMin = improvedMin;
    }

    public double getOriginalMin() {
        return originalMin;
    }

    public void setOriginalMin(double originalMin) {
        this.originalMin = originalMin;
    }

    public double getImprovedAve() {
        return improvedAve;
    }

    public void setImprovedAve(double improvedAve) {
        this.improvedAve = improvedAve;
    }

    public double getOriginalAve() {
        return originalAve;
    }

    public void setOriginalAve(double originalAve) {
        this.originalAve = originalAve;
    }

    public double getImprovedStd() {
        return improvedStd;
    }

    public void setImprovedStd(double improvedStd) {
        this.improvedStd = improvedStd;
    }

    public double getOriginalStd() {
        return originalStd;
    }

    public void setOriginalStd(double originalStd) {
        this.originalStd = originalStd;
    }

    public double getImprovedMax() {
        return improvedMax;
    }

    public void setImprovedMax(double improvedMax) {
        this.improvedMax = improvedMax;
    }

    public double getOriginalMax() {
        return originalMax;
    }

    public void setOriginalMax(double originalMax) {
        this.originalMax = originalMax;
    }
}
