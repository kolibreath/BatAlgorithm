import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


public class BatInspiredAlgorithm {
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
    }

    //一个求值函数

    private double f(){
        //todo 运算精度
      return  (1/Math.E)*Math.pow(Math.E,((generation - t)/generation));
    }
    private void changePossibility(){
        LinkedList<Double> weights = new LinkedList<>();
        for (int i = 0; i < windowSize; i++) {
            double weightVariable = 0.95;
            weights.add(Math.pow(weightVariable,i+1));
        }

        int counter = 0;
        double end = possibility.get(counter);
        LinkedList<Double> temp = new LinkedList<>();
        while(counter + 1 < windowSize){
            end += (1 - end) * f() * weights.get(counter) * 1/40;
            temp.add(end);

            if(counter == windowSize -2)break;
            end = possibility.get(counter + 1);
            counter ++;
        }



    }
    IFunctions ff;

    public BatInspiredAlgorithm(IFunctions iff, int in, int iNgen, double iA, double ir, double iQmin, double iQmax, double[] iLbvec, double[] iUbvec) {
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
        double d1[] = getminval_index(fitness);
        fmin = d1[0];
        int index = (int) d1[1];
        best = batPopulationLocation[index];

        int counter = 0;
        double lastEnd = 0;
        while (counter + 1 < windowSize) {
            double[] random = batPopulationLocation[randomIndex()];
            Window window = new Window(random,ff.func(random));

            lastEnd =  (1.0/windowSize)*(counter + 1);
            possibility.add(lastEnd);
            windows.add(window);
            counter ++;
        }

        double[] location = batPopulationLocation[randomIndex()];
        Window window = new Window(location,ff.func(location));
        windows.add(window);
        Collections.sort(windows);
    }
	 
		 double[] getminval_index(double[] a) {
			 double m=0.0;
			 double b[]=new double[a.length];
			 for(int i=0;i<a.length;i++)
			 {b[i]=a[i];}
			 double minval=a[0];
			 for(int i=0;i<a.length;i++)
			 {if(a[i]<minval){minval=a[i];}}
			 for(int i=0;i<a.length;i++)
			 {if(b[i]==minval){m=i;break;}};
			 double[] dep=new double[2];
			 dep[0]=minval;
			 dep[1]=m;
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
			  double alfa=0.5264;
			  double gamma=4.411;
			  double A0=0.5026;
			  double r0=0.4205;
			  Random rndm=new Random();
			  double fnew=0.0;
			  while(t< generation) {

			    changePossibility();

				for(int i = 0; i< population; i++) {
                    //todo fitness 暂时没有改动
                    //这里将best[]
                    best = windows.get(randomIndex()).getLocation();
                    Q[i] = Qmin + (Qmin - Qmax) * Math.random();
                    for (int j = 0; j < d; j++) {
                        v[i][j] = v[i][j] + ((batPopulationLocation[i][j] - best[j]) * Q[i]);
                        S[i][j] = batPopulationLocation[i][j] + v[i][j];
                    }
                    batPopulationLocation[i] = simplebounds(batPopulationLocation[i]);
                    //System.out.println(Matrix.toString(batPopulationLocation[i]));
                    if (Math.random() > r0) {
                        for (int j = 0; j < d; j++) {
                            S[i][j] = best[j] + (0.001 * rndm.nextGaussian());
                        }
                    }
                    fnew = ff.func(S[i]);

                    if ((fnew <= fitness[i]) && (Math.random() < A0)) {
                        for (int j = 0; j < d; j++) {
                            batPopulationLocation[i][j] = S[i][j];
                        }
                        fitness[i] = fnew;
                        r0 = r0 * (1.0 - Math.exp(-gamma * t));
                        A0 = A0 * alfa;
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
	
		void toStringnew() {
			double[][] out=solution();
			System.out.println("Optimized value = "+out[0][0]);
			for(int i=0;i<d;i++) {
				System.out.println("x["+i+"] = "+out[1][i]);
			}
		}

}