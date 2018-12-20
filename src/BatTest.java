//http://extreme.adorio-research.org
public class BatTest {
public static void main(String args[]) {

	double[] Lower={-500.0,-500.0,-500.0};//  f11 griewank
	double[] Upper={500.0,500.0,500.0};



             			   
		                 
		int n=500;//population size;
	    int Ngen=1000;// Number of generation
	    double A=0.45;// Loudness;
	    double r=0.5;// Pulse rate
	    double Qmin=0.0;//Frequency minimum
	    double Qmax=2.0;//Frequency maximum

	    ImplementedFunctions functions = new ImplementedFunctions();
	    functions.setIndex(0);

	    BatInspiredAlgorithm bat=new BatInspiredAlgorithm(functions,n,Ngen,A,r,Qmin,Qmax,Lower,Upper);
	    bat.toStringnew();

	 } 
}