package cn.edu.ccnu.kolibreath.al_viewer.model;


import java.util.List;

public class ParticlesWrapper {
    private List<Particle> original;
    private List<Particle> improved;

    public List<Particle> getOriginal() {
        return original;
    }

    public void setOriginal(List<Particle> original) {
        this.original = original;
    }

    public List<Particle> getImproved() {
        return improved;
    }

    public void setImproved(List<Particle> improved) {
        this.improved = improved;
    }
}
