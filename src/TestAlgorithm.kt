import java.util.*

class TestAlgorithm(private val i:Int) :BatAlgorithm() {


    override fun objective(xi: DoubleArray): Double {
        val function = Functions()
        return function.runAll(xi,dimension,i)
    }
}

fun main(args:Array<String>) {

//
//    val Lower = doubleArrayOf(-500.0, -500.0, -500.0)
//    val Upper = doubleArrayOf(500.0, 500.0, 500.0)

    val Lower = doubleArrayOf(-10.0, -10.0, -10.0)//  f11 griewank
    val Upper = doubleArrayOf(10.0, 10.0, 10.0)

    val n = 20//population size;
    val Ngen = 1000// Number of generation
    val A = 0.45// Loudness;
    val r = 0.5// Pulse rate
    val Qmin = 0.0//Frequency minimum
    val Qmax = 2.0//Frequency maximum
    lateinit var batAlgorithm: AbsBatAlgorithm

    val functions = ImplementedFunctions()
    functions.index = 1

    val container = LinkedList<Double>()
    repeat(30){
        batAlgorithm = OriginalBatAlgorithm(functions,n,Ngen,A,r,Qmin,Qmax,Lower,Upper)
        container.add(batAlgorithm.bestValue())
    }

    println("the best average value of original bat is ${container.min()}")

    container.clear()

    repeat(30){
        batAlgorithm = ImprovedBatAlgorithm(functions, n, Ngen, A, r, Qmin, Qmax, Lower, Upper)
        container.add(batAlgorithm.bestValue())
    }

    println("the best average value of improved bat is ${container.min()}")

}