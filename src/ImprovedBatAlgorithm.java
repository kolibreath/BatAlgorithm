import functions.IFunctions;

import java.io.File;
import java.util.*;


public class ImprovedBatAlgorithm extends AbsBatAlgorithm{
    //t表示当前迭代次数
    int population,t = 1;
    private int generation;
    private double A;
    private double r;
    private double Qmin;
    private double Qmax;
    private int Niter;
    private int d;
    private double Lb;
    private double Ub;
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

    private File file;


    private double loundnesses[];
    private LinkedList<Double> possibility=new LinkedList<>();
    private int windowSize = 4;
    private LinkedList<Window> windows = new LinkedList<>();


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
//        return 0;
    }

    //一个求值函数

    private double f(){
        //todo 运算精度
      return  (1/Math.E)*Math.pow(Math.E,((t)/generation));
    }
    private void changePossibility(){
        LinkedList<Double> weights = new LinkedList<>();
        for (int i = 0; i < windowSize; i++) {
            double weightVariable = 0.80;
            weights.add(Math.pow(weightVariable,i));
        }

        int counter = 0;
        double end = possibility.get(counter);
        LinkedList<Double> temp = new LinkedList<>();
        while(counter + 1 < windowSize){
            end -= (1 - end) * f() * weights.get(counter) * 1/0100;
//            end += (1 - end)  * weights.get(counter) * 1/100;
            temp.add(end);

            if(counter == windowSize -2)break;
            end = possibility.get(counter + 1);
            counter ++;
        }

        possibility = temp;

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
            loundnesses[i] = Math.random() + 1;
        }
    }

    private int randomIndex(){
       Random random = new Random();
       return Math.abs(random.nextInt()) %   population;
    }

    private	void initialize() {
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

        double firstSection = 0.7;
        double section = (1.0 - firstSection)/(windowSize-1);

//        for (int i = 0; i < v.length; i++) {
//            for (int j = 0; j < v[0].length; j++) {
//                v[i][j] = Math.random() * 2 -1;
//            }
//        }

        int counter = 0;
        double lastEnd = firstSection;
        int indices[] = getSortedFitnessIndices(fitness);
        while (counter + 1 < windowSize) {
            //todo 应该改成best 而不是随机值
            double ranger[] = batPopulationLocation[indices[counter]];
            Window window = new Window(ranger
                    ,ff.func(ranger));

            possibility.add(lastEnd);
            lastEnd = firstSection +  (section)*(counter + 1);
            windows.add(window);
            counter ++;
        }

        double[] location = batPopulationLocation[indices[counter]];
        Window window = new Window(location,ff.func(location));
        windows.add(window);
        Collections.sort(windows);
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
                    else if(aDouble == t1)
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
        ;
        double[] dep = new double[2];
        dep[0] = minval;
        dep[1] = m;
        return dep;
    }

		 double[] simplebounds(double s[]) {
			   for(int i=0;i<d;i++)
			   {if(s[i]<= lb[i])
				{s[i]= lb[i];}
				if(s[i]>= ub[i])
				{s[i]= ub[i];}
			   }
			   return s;
		 }
	 
		 double[][] solution() {

			  initialize();

			  //file initial file
             file = new File(getFilename());

             double alfa=0.5264;
             double gamma=4.411;
			  double A0=0.5026;
			  double r0=0.4205;
			  Random rndm=new Random();
			  double fnew=0.0;


			  while(t< generation) {

			    changePossibility();
//                System.out.println(possibility);

				for(int i = 0; i< population; i++) {

                      A0 = loundnesses[i];
                    //todo fitness 暂时没有改动
//                    这里将best[]
                   best = windows.get(randomTarget()).getLocation();
                    Q[i] = Qmin + (Qmin - Qmax) * Math.random();
                    for (int j = 0; j < d; j++) {
                        v[i][j] = v[i][j] + ((batPopulationLocation[i][j] - best[j]) * Q[i]);
                        S[i][j] = batPopulationLocation[i][j] + v[i][j];
                    }
                    batPopulationLocation[i] = simplebounds(batPopulationLocation[i]);
                    if (Math.random() > r0) {
                        for (int j = 0; j < d; j++) {
                            double eth = Math.random()*2 - 1;
//                        S[i][j] = best[j] + (0.001 * rndm.nextGaussian());
                            S[i][j] = best[j] + eth*aveLoundness();
                        }
                    }
                    //todo 对于S 的位置要进行收敛
                    //todo 已经确定上界和下界 要进行改良 和 观察in
                    fnew = ff.func(simplebounds(S[i]));

                    //修改
                    if ((fnew <= fitness[i])
                            ) {
                        for (int j = 0; j < d; j++) {
                            batPopulationLocation[i][j] = S[i][j];
                        }
                        fitness[i] = fnew;
                        r0 = r0 * (1.0 - Math.exp(-gamma * t));
                        loundnesses[i]= A0 * alfa;
                    }

                    if (fnew <= fmin) {
                        for (int j = 0; j < d; j++) {
                            best[j] = S[i][j];
                        }
                        fmin = fnew;
                        useWindows(best);
                    }
                }


				BEST[t]=fmin;
				t++;
		}



		double[] plott=new double[generation];
		for(int i = 0; i< generation; i++) {
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
        return Arrays.stream(loundnesses).sum();
    }


	//增加并且排序
	private void useWindows(double[]location){
        Window maxValue = windows.stream().max(Window::compareTo).get();
        if(ff.func(location) < maxValue.getObjectives()){
            windows.remove(maxValue);
            windows.add(new Window(location,ff.func(location)));
        }
        windows.sort(Window::compareTo);
    }
	public 	void toStringnew() {
        double[][] out = solution();
        System.out.println("Optimized value = " + out[0][0]);
        for (int i = 0; i < d; i++) {
            System.out.println("x[" + i + "] = " + out[1][i]);
        }
    }

    @Override
    double bestValue() {
        return solution()[0][0];
    }
}