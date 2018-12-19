import java.util.Random;
////////////////////////////////////////////////////
// Bat inspired algorithm for continous optimisation
// Xin-She Yang ////////////////////////////////////
////////////////////////////////////////////////////
//REFERANCE
//1) Yang X.-S., A new metaheuristic bat-inspired algorithm,        
//    in: Nature Inspired Cooperative Strategies for Optimization    
//   (NISCO 2010) (Eds. J. R. Gonzalez et al.), Studies in          
//   Computational Intelligence, Springer, vol. 284, 65-74 (2010).  
// 2) Yang X.-S., Nature-Inspired Metaheuristic Algorithms,          
//    Second Edition, Luniver Presss, Frome, UK. (2010).             
// 3) Yang X.-S. and Gandomi A. H., Bat algorithm: A novel           
//   approach for global engineering optimization,                  
//   Engineering Computations, Vol. 29, No. 5, pp. 464-483 (2012).  
/////////////////////////////////////////////////////////////////////


public class BatInspiredAlgorithm {
		int n;
		int Ngen;
		double A;
		double r;
		double Qmin;
		double Qmax;
		int Niter;
		int d;
		double Lb;
		double Ub;
		double Lbvec[];
		double Ubvec[];
		double Q[];
		double v[][];
		double[][] Sol;
		double[][] S;
		double[] fitness;
		double[] best;
		double BEST[];
		double BESTvar1[];
		double BESTvar2[];
		double BESTvar3[];
		double fmin;
IFunctions ff;

		public BatInspiredAlgorithm(IFunctions iff, int in, int iNgen, double iA, double ir, double iQmin, double iQmax, double[] iLbvec, double[] iUbvec) {
	 n=in;
	 Ngen=iNgen;
	 A=iA;
	 r=ir;
	 Qmin=iQmin;
	 Qmax=iQmax;
	 BEST=new double[Ngen];
	 BESTvar1=new double[Ngen];
	 BESTvar2=new double[Ngen];
	 BESTvar3=new double[Ngen];
	 Lbvec=iLbvec;
	 Ubvec=iUbvec;
	 d=Lbvec.length;
	 ff=iff;
	 
	 Sol=new double[n][d];
	 S=new double[n][d];
	 fitness=new double[n];
	 best=new double[d];
	 
	 Q=new double[n];
	 v=new double[n][d];
}
	 
		 void initialize() {
		 for(int i=0;i<n;i++) {
		 	for(int j=0;j<d;j++) {
		 		Sol[i][j]=Lbvec[j]+(Ubvec[j]-Lbvec[j])*Math.random();
		 	}
			fitness[i]=ff.func(Sol[i]);}
			double d1[]=getminval_index(fitness);
			fmin=d1[0];
			int index=(int)d1[1];
			best=Sol[index];
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
			   {if(s[i]<=Lbvec[i])
				{s[i]=Lbvec[i];}
				if(s[i]>=Ubvec[i])
				{s[i]=Ubvec[i];}
			   }
			   return s;
		 }
	 
		 double[][] solution() {
			  int t=1;
			  initialize();
			  double alfa=0.5264;
			  double gamma=4.411;
			  double A0=0.5026;
			  double r0=0.4205;
			  Random rndm=new Random();
			  double fnew=0.0;
			  while(t<Ngen) {
				for(int i=0;i<n;i++) {
				  Q[i]=Qmin+(Qmin-Qmax)*Math.random();
				  for(int j=0;j<d;j++)
				  {v[i][j]=v[i][j]+((Sol[i][j]-best[j])*Q[i]);
				   S[i][j]=Sol[i][j]+v[i][j];
				  }
				  Sol[i]=simplebounds(Sol[i]);
				  //System.out.println(Matrix.toString(Sol[i]));
				  if(Math.random()>r0)
				  {for(int j=0;j<d;j++)
				   {S[i][j]=best[j]+(0.001*rndm.nextGaussian());}
				  }
				  fnew=ff.func(S[i]);

				  if((fnew<=fitness[i])&&(Math.random()<A0))
				  { for(int j=0;j<d;j++)
					 {Sol[i][j]=S[i][j];}
					  fitness[i]=fnew;
					  r0=r0*(1.0-Math.exp(-gamma*t));
					  A0=A0*alfa;
				  }

				  if(fnew<=fmin)
				  {for(int j=0;j<d;j++)
				   {best[j]=S[i][j];}
					fmin=fnew;}
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
	
		void toStringnew() {
			double[][] out=solution();
			System.out.println("Optimized value = "+out[0][0]);
			for(int i=0;i<d;i++) {
				System.out.println("x["+i+"] = "+out[1][i]);
			}
		}
}