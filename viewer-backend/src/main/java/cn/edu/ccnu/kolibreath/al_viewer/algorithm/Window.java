package cn.edu.ccnu.kolibreath.al_viewer.algorithm;

import org.jetbrains.annotations.NotNull;

public class Window implements Comparable<Window>{

    private double objectives;
    private double[] location;

    public double getObjectives() {
        return objectives;
    }

    public void setObjectives(double objectives) {
        this.objectives = objectives;
    }

    public double[] getLocation() {
        return location;
    }

    public void setLocation(double[] location) {
        this.location = location;
    }

    public Window(double[] location,double objectives){
        this.objectives = objectives;
        this.location = location;
    }

    @Override
    public int compareTo(@NotNull Window other) {
        if((this.objectives - other.objectives) > 0)
            return 1;
        else
            return -1;
    }

}
