package cn.edu.ccnu.kolibreath.al_viewer.model;

import java.util.List;

public class ConfigWrapper {
    private int population;
    private int generation;
    private double pulseRate;
    private double frequency;
    private List<Integer> functionQueue;
    private int functionIndex;
    private double loudness;

    private double speed;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }

    public double getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(double pulseRate) {
        this.pulseRate = pulseRate;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public List<Integer> getFunctionQueue() {
        return functionQueue;
    }

    public void setFunctionQueue(List<Integer> functionQueue) {
        this.functionQueue = functionQueue;
    }

    public int getFunctionIndex() {
        return functionIndex;
    }

    public void setFunctionIndex(int functionIndex) {
        this.functionIndex = functionIndex;
    }

    public double getLoudness() {
        return loudness;
    }

    public void setLoudness(double loudness) {
        this.loudness = loudness;
    }
}
