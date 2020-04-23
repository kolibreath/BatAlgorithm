package cn.edu.ccnu.kolibreath.al_viewer.algorithm;

import cn.edu.ccnu.kolibreath.al_viewer.algorithm.functions.IFunctions;
import com.sun.java.swing.plaf.windows.resources.windows;

import java.io.File;
import java.util.*;


@SuppressWarnings({"SingleStatementInBlock", "Duplicates"})
public class ImprovedBatAlgorithm extends AbsBatAlgorithm{
    //t表示当前迭代次数
    int population,t = 1;
    private int generation;
    private double A;
    private double r;
    private double Qmin;
    private double Qmax;
    private int d;
    private double lb[];
    private double ub[];
    private double Q[];
    private double v[][];
    private double[][] batPopulationLocation;
    private double[][] S;
    private double[] fitness;
    private double[] best;
    private double BEST[];
    private double BESTvar1[];
    private double BESTvar2[];
    private double BESTvar3[];
    private double fmin;

    private double firstSection = 0.8;

    //和origin中的bat的操作相同
    private int imporvedCounter = 0;

    private File file;

    Random random = new Random();

    private double loundnesses[];
    private LinkedList<Double> possibility=new LinkedList<>();
    private int windowSize = 4;

    private int recordTime = 20;
    private LinkedList<Window> windows = new LinkedList<>();
    private double lastModify = 0.0;
    private LinkedList<Window> recordWindows = new LinkedList<>();

    //所有的都不变 返回true 只要又一次变动 返回false
    private boolean isEqual() {
        for (int i = 0; i < windows.size(); i++) {
            Window lastWindow = recordWindows.get(i);
            Window curWindow = windows.get(i);

            if (lastWindow.getObjectives() != curWindow.getObjectives()) {
                return false;
            }
        }
        return true;
    }

    private void watchWindows(){
        //如果现在的windows和记录相比有所变化
        if( !isEqual()){
            recordWindows = new LinkedList<>(windows);
            lastModify = t;
        }
        if(isEqual() && t - lastModify >= (recordTime + t/5)){

            recordWindows.clear();
                  for (Window curWindow : windows) {

                      random = new Random();

                      int powCounter = 1;
                      double curLocation[] = new double[d];
                      for (int i = 0; i < d; i++) {
                          curLocation[i] = curWindow.getLocation()[i];
                      }

                      for (int j = 0; j < curLocation.length; j++) {
                          double eth = Math.random() * 2 - 1;
                          curLocation[j] += eth * aveLoundness() * 0.001;
                      }

                      Window window = new Window(curLocation, ff.func(curLocation));
                      recordWindows.add(window);
                  }
            windows = new LinkedList<>(recordWindows);
            lastModify = t;
        }
    }

    private int randomTarget(){
        double d = Math.random();
        int index = 0;
        while(index < windowSize -1){
            if(d <= possibility.get(index)){
                return index;
            }
            index ++;
        }
        return index;
    }

    //一个求值函数

    private double f(){
        //todo 运算精度
      return  (1/Math.E)*Math.pow(Math.E,((t)/generation));
    }



    private void changePossibility(){
        double temp  = firstSection -  (firstSection - ( 1.0/ windowSize)) * t / generation;
        double rest  = ( 1 - temp) / ( windowSize - 1);
        possibility.clear();
        possibility.add(temp);
        for (int i = 0; i < windowSize - 1 ; i++) possibility.add(rest);
//        System.out.println("possibility" + possibility);
    }

    IFunctions ff;

    public ImprovedBatAlgorithm(IFunctions iff, int in, int iNgen, double iA, double ir, double iQmin, double iQmax, double[] iLbvec, double[] iUbvec) {
        population = in;
        generation = iNgen;
        A = iA;
        r = ir;
        Qmin = iQmin;
        Qmax = iQmax;
        BEST = new double[generation];
        BESTvar1 = new double[generation];
        BESTvar2 = new double[generation];
        BESTvar3 = new double[generation];
        lb = iLbvec;
        ub = iUbvec;
        d = lb.length;
        ff = iff;

        batPopulationLocation = new double[population][d];
        S = new double[population][d];
        fitness = new double[population];
        best = new double[d];

        Q = new double[population];
        v = new double[population][d];

        loundnesses = new double[population];
        for (int i = 0; i <loundnesses.length ; i++) {
            loundnesses[i] = Math.random() + 1 ;
        }
    }

    private int randomIndex(int size){
       Random random = new Random();
       return Math.abs(random.nextInt()) %   size;
    }

    public	void initialize() {
        for (int i = 0; i < population; i++) {
            for (int j = 0; j < d; j++) {
                batPopulationLocation[i][j] = lb[j] + (ub[j] - lb[j]) * Math.random();
            }
            fitness[i] = ff.func(batPopulationLocation[i]);
        }
        double d1[] = getMinValue(fitness);
        fmin = d1[0];
        int index = (int) d1[1];
        best = batPopulationLocation[index];

        //todo check initial
        double section = (1.0 - firstSection) / (windowSize - 1);


        int counter = 0;
        double lastEnd = firstSection;
        int indices[] = getSortedFitnessIndices(fitness);
        while (counter + 1 < windowSize) {
            //todo 应该改成best 而不是随机值
            double ranger[] = batPopulationLocation[indices[counter]];

            Window window = new Window(ranger
                    , ff.func(ranger));

            possibility.add(lastEnd);
            lastEnd = firstSection + (section) * (counter + 1);
            windows.add(window);
            counter++;
        }

        double[] location = batPopulationLocation[indices[counter]];
        Window window = new Window(location, ff.func(location));
        windows.add(window);
        Collections.sort(windows);

        recordWindows = new LinkedList<Window>(windows);
    }
    private  int[] getSortedFitnessIndices(double fitness[]){
        int indices[]= new int[fitness.length];
        HashMap<Double,Integer> map = new HashMap<>();
        for(int i = 0;i<fitness.length;i++){
            map.put(fitness[i],i);
        }

        Map<Double,Integer> sortedMap = new TreeMap<>(
                (aDouble, t1) -> {
                    if (aDouble - t1 >0)
                        return 1;
                    else if(aDouble.equals(t1))
                        return 0;
                    else
                        return -1;
                }
        );

        sortedMap.putAll(map);
         int counter = 0;
         for(Map.Entry<Double,Integer> entry: sortedMap.entrySet()){
             indices[counter++] = entry.getValue();
         }
         return indices;
    }

    private double[] getMinValue(double[] a) {

        double m = 0.0;
        double b[] = new double[a.length];
        for (int i = 0; i < a.length; i++) {
            b[i] = a[i];
        }
        double minval = a[0];
        for (int i = 0; i < a.length; i++) {
            if (a[i] < minval) {
                minval = a[i];
            }
        }

        for (int i = 0; i < a.length; i++) {
            if (b[i] == minval) {
                m = i;
                break;
            }
        }
        double[] dep = new double[2];
        dep[0] = minval;
        dep[1] = m;
        return dep;
    }

    private double[] simplebounds(double s[]) {
			   for(int i=0;i<d;i++)
			   {if(s[i]<= lb[i])
				{s[i]= lb[i];}
				if(s[i]>= ub[i])
				{s[i]= ub[i];}
			   }
			   return s;
		 }

	private void solutionWrapper(){
        initialize();

        double alfa = 0.5264;
        double gamma = 4.411;

        //todo 玻璃！
        double pulseRate[] = new double[population];
        for (int i = 0; i < population; i++) {
            pulseRate[i] = Math.random();
        }

        int tWrapper[] = {0};
        while(tWrapper[0] < generation){
            solutionEachGeneration(tWrapper, pulseRate, gamma, alfa);
        }
    }

    public double[][] solutionEachGeneration(int []tWrapper, double[] pulseRate, double gamma,
                                             double alfa) {

        changePossibility();
        watchWindows();

        Collections.sort(windows);

        if(tWrapper[0] % 5 == 0)
            System.out.println(fmin);
        for (int i = 0; i < population; i++) {
            for (int k = 0; k < windowSize; k++) {
                windows.get(k).setObjectives(ff.func(windows.get(k).getLocation()));
            }

            double r0, A0, fnew;
            r0 = pulseRate[i];
            A0 = loundnesses[i];

            best = windows.get(randomTarget()).getLocation();
            Q[i] = Qmin + (Qmin - Qmax) * Math.random();
            for (int j = 0; j < d; j++) {
                v[i][j] = v[i][j] + ((batPopulationLocation[i][j] - best[j]) * Q[i]);
                S[i][j] = batPopulationLocation[i][j] + v[i][j];
            }
            batPopulationLocation[i] = simplebounds(batPopulationLocation[i]);
            if (Math.random() > r0) {
                for (int j = 0; j < d; j++) {
                    double eth = Math.random() * 2 - 1;
//                        S[i][j] = best[j] + (0.001 * random.nextGaussian());
                    S[i][j] = best[j] + eth * aveLoundness();
                }
            }

            fnew = ff.func(simplebounds(S[i]));
            imporvedCounter++;

            //修改
            if ((fnew <= fitness[i])) {
                System.arraycopy(S[i], 0, batPopulationLocation[i], 0, d);
                fitness[i] = fnew;
                pulseRate[i] = r0 * (1.0 - Math.exp(-gamma * t));
                loundnesses[i] = A0 * alfa;
            }

            if (fnew <= fmin) {
                System.arraycopy(S[i], 0, best, 0, d);
                fmin = fnew;
                useWindows(best);
            }
        }

        BEST[tWrapper[0]] = windows.get(0).getObjectives();
        tWrapper[0]++;
        return batPopulationLocation;
    }

    private double aveLoundness(){
        return Arrays.stream(loundnesses).sum();
    }


	//增加并且排序
	private void useWindows(double[]location){
        double sum = ff.func(windows.get(0).getLocation());
        int index = 0;
        for(int i = 0; i< windows.size();i++){
            if(ff.func(windows.get(i).getLocation()) > sum){
                index = i;
                sum = ff.func(windows.get(i).getLocation());
            }
        }
        if(ff.func(location) < ff.func(windows.get(index).getLocation())){
            windows.remove(index);
            windows.add(new Window(location,ff.func(location)));
        }
        Collections.sort(windows);
    }

    @Override
    public double bestValue() {
        solutionWrapper();
        return fmin;
    }
}