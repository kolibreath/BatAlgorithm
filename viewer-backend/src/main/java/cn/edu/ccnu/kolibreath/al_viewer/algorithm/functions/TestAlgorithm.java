package cn.edu.ccnu.kolibreath.al_viewer.algorithm.functions;

import cn.edu.ccnu.kolibreath.al_viewer.algorithm.AbsBatAlgorithm;
import cn.edu.ccnu.kolibreath.al_viewer.algorithm.ImprovedBatAlgorithm;
import cn.edu.ccnu.kolibreath.al_viewer.algorithm.OriginalBatAlgorithm;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TestAlgorithm {

   public static void main(String args[]){
       double[] lower = new double[]{-10.0, -10.0, -10.0};
       double[] upper = new double[]{10.0, 10.0, 10.0};

       int Ngen  =100;
       double A = 0.45;
       int n = 20;
       double r = 0.5;
       double Qmin = 0.0;
       double Qmax = 2.0;

       AbsBatAlgorithm batAlgorithm;
       ImplementedFunctions functions  = new ImplementedFunctions();

       //设置函数的下标
       int indexControl = 4;
       printOrigin();
       for (int i = 0; i < 1; i++){
           printIndex(i);
           functions.setIndex(indexControl);
           List<Double> container = new LinkedList<>();
           for (int j = 0; j < 30; j++) {
               batAlgorithm = new OriginalBatAlgorithm(functions, n ,Ngen, A, r, Qmin, Qmax ,lower, upper);
               double bestValue = batAlgorithm.bestValue();
               System.out.println(bestValue);
               container.add(bestValue);
           }

           println("");
           println("the worst   value is" + Collections.max(container));
           println("the best    value is" + Collections.min(container));
           println("the average value is" + container.stream().mapToDouble( d -> d).average().getAsDouble());

       }

       printImprove();
       for (int i = 0; i < 1; i++){
           printIndex(i);
           functions.setIndex(indexControl);
           List<Double> container = new LinkedList<>();
           for (int j = 0; j < 30; j++) {
               batAlgorithm = new ImprovedBatAlgorithm(functions, n ,Ngen, A, r, Qmin, Qmax ,lower, upper);
               double bestValue = batAlgorithm.bestValue();
               System.out.println(bestValue);
               container.add(bestValue);
           }

           println("");
           println("the worst   value is" + Collections.max(container));
           println("the best    value is" + Collections.min(container));
           println("the average value is" + container.stream().mapToDouble( d -> d).average().getAsDouble());

       }

   }

   private static void printOrigin(){
       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ORIGIN~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

   }

   private static void printImprove(){
       System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IMPROVE~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
   }
   private static void printIndex(int indeex){
       System.out.println("-------------------------------"+indeex+"---------------------------");
   }

   private static void println(String message){
       System.out.println(message);
   }
   
}
