package cn.edu.ccnu.kolibreath.al_viewer.algorithm;

import functions.IFunctions;

import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class OriginalBatAlgorithm extends AbsBatAlgorithm{
    private int population;
    private int Ngen;
    private double A;
    private double r;
    private double Qmin;
    private double Qmax;
    private int Niter;
    private int d;
    private double Lb;
    private double Ub;
    private double Lbvec[];
    private double Ubvec[];
    private double Q[];
    private double v[][];
    private double[][] Sol;
    private double[][] S;
    private double[] fitness;
    private double[] best;
    private double BEST[];
    private double fmin;
    private IFunctions ff;

    //评估函数调用次数计数
    private int counter = 0;
    private File file;

    double[] loundnesses ;
    public OriginalBatAlgorithm(IFunctions iff, int in, int iNgen, double iA, double ir, double iQmin, double iQmax, double[] iLbvec, double[] iUbvec) {
        population =in;
        Ngen=iNgen;
        A=iA;
        r=ir;
        Qmin=iQmin;
        Qmax=iQmax;
        BEST=new double[Ngen];
        Lbvec=iLbvec;
        Ubvec=iUbvec;
        d=Lbvec.length;
        ff=iff;

        Sol=new double[population][d];
        S=new double[population][d];
        fitness=new double[population];
        best=new double[d];

        Q=new double[population];

        loundnesses = new double[population];
        for (int i = 0; i <loundnesses.length ; i++) {
            loundnesses[i] = Math.random() + 1;
        }
        v=new double[population][d];
    }

    void initialize() {
        for(int i = 0; i< population; i++) {
            for(int j=0;j<d;j++) {
                Sol[i][j]=Lbvec[j]+(Ubvec[j]-Lbvec[j])*Math.random();
            }
            fitness[i]=ff.func(Sol[i]);}
        double d1[]= getMinValue(fitness);
        fmin=d1[0];
        int index=(int)d1[1];
        best=Sol[index];

    }

    double[] getMinValue(double[] a) {
        double m=0.0;
        double b[]=new double[a.length];
        for(int i=0;i<a.length;i++)
        {b[i]=a[i];}
        double minval=a[0];
        for(int i=0;i<a.length;i++)
            if (a[i] < minval) minval = a[i];
        for(int i=0;i<a.length;i++)
        {if(b[i]==minval){m=i;break;}};
        double[] dep=new double[2];
        dep[0]=minval;
        dep[1]=m;
        return dep;
    }

    double[] simplebounds(double s[]) {
        for(int i=0;i<d;i++)
        {if(s[i]<Lbvec[i])
        {s[i]=Lbvec[i];}
            if(s[i]>Ubvec[i])
            {s[i]=Ubvec[i];}
        }
        return s;
    }


    private int randomIndex(){
        Random random = new Random();
        return Math.abs(random.nextInt()) %   population;
    }
    private double[][] solution() {

        file = new File(getFilename());


        int t=1;
        initialize();
        double alfa=0.5264;
        double gamma=4.411;
        double A0=0.5026;
        double r0=0.4205;
        Random rndm=new Random();
        double fnew=0.0;
        while(t<Ngen) {
            if(counter % 1000 ==0)
                FileUtils.Companion.write(file,fmin + "\n");

            for(int i = 0; i< population; i++) {
                //改变这只蝙蝠的平均响度
                A0 = loundnesses[i];
                Q[i] = Qmin + (Qmin - Qmax) * Math.random();
                for (int j = 0; j < d; j++) {
                    v[i][j] = v[i][j] + ((Sol[i][j] - best[j]) * Q[i]);
                    S[i][j] = Sol[i][j] + v[i][j];
                }
                Sol[i] = simplebounds(Sol[i]);


                if (Math.random() > r0) {
                    for (int j = 0; j < d; j++) {
                        double eth = Math.random()*2 - 1;
//                        S[i][j] = best[j] + (0.001 * rndm.nextGaussian());
                        S[i][j] = best[j] + eth*aveLoundness();
                    }
                }
                //每调用一次评估函数counter ++ 记录 1000 2000 3000 ... 的结果
                fnew = ff.func(simplebounds(S[i]));
                counter++;


                if ((fnew <= fitness[i]) && (Math.random() < A0)) {
                    System.arraycopy(S[i], 0, Sol[i], 0, d);
                    fitness[i] = fnew;
                    r0 = r0 * (1.0 - Math.exp(-gamma * t));
                    //改变这只蝙蝠的响度
                    loundnesses[i] = A0 * alfa;

                }

                if (fnew <= fmin) {
                    for (int j = 0; j < d; j++) {
                        best[j] = S[i][j];
                    }
                    fmin = fnew;
                }



            }

            BEST[t]=fmin;
            t++;
        }


        double[] plott=new double[Ngen];
        for(int i=0;i<Ngen;i++) {
            plott[i]=i;
        }


        double[][] dep=new double[2][d];
        dep[0][0]=fmin;
        for(int i=0;i<d;i++) {
            dep[1][i]=best[i];
        }
        return dep;
    }

    private double aveLoundness(){
//        System.out.println(Arrays.stream(loundnesses).sum());
        return Arrays.stream(loundnesses).sum();
    }

    void toStringnew() {
        double[][] out = solution();
        System.out.println("Optimized value = " + out[0][0]);
        for (int i = 0; i < d; i++) {
            System.out.println("x[" + i + "] = " + out[1][i]);
        }
    }

    double bestValue (){
        return solution()[0][0];
    }
}