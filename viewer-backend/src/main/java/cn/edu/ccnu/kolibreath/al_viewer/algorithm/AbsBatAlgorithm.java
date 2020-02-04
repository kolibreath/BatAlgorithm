package cn.edu.ccnu.kolibreath.al_viewer.algorithm;

import java.io.File;

public class AbsBatAlgorithm {
    private  String fileName ;
    private File recordFile;
    public double bestValue(){
        return 0;
    }

    /**
     * 根据传进来的参数初始化file ，并且删除已经存在的文件
     * @param fileName
     */
    public void initFile(String fileName) {
        File file = new File(fileName);
        this.fileName = fileName;
        if (file == null)
            return;
        else {
            file.delete();
            recordFile = file;
        }
    }

    public void append2File(){
        if(recordFile ==null)return ;
    }

}
