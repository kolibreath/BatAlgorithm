package cn.edu.ccnu.kolibreath.al_viewer.algorithm;

abstract public class AbsBatAlgorithm {
    String fileName ;
    abstract void toStringnew();

    abstract double bestValue();

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFilename(){
        return fileName;
    }
}
