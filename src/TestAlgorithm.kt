import functions.Functions
import functions.ImplementedFunctions
import java.util.*

fun main(args:Array<String>) {

    var Lower = doubleArrayOf(-10.0, -10.0, -10.0)//  f11 griewank
    var Upper = doubleArrayOf(10.0, 10.0, 10.0)

    val n = 20//population size;
    val Ngen = 1000// Number of generation
    val A = 0.45// Loudness;
    val r = 0.5// Pulse rate
    val Qmin = 0.0//Frequency minimum
    val Qmax = 2.0//Frequency maximum
    lateinit var batAlgorithm: AbsBatAlgorithm

    val functions = ImplementedFunctions()


    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ORIGIN~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~`")
    repeat(12) {
        println("--------------------------$it---------------------------------")
        functions.index = it

        val container = LinkedList<Double>()
        repeat(30) {

            batAlgorithm = OriginalBatAlgorithm(functions, n, Ngen, A, r, Qmin, Qmax, Lower, Upper)
            val bestValue = batAlgorithm.bestValue()
            println("origin best value $bestValue")
            container.add(bestValue)
        }

        println()
        println("the worst   value is ${container.max()}")
        println("the best    value is ${container.min()}")
        println("the average value is ${container.average()}")
    }


    Lower = doubleArrayOf(-500.0, -500.0, -500.0)
    Upper = doubleArrayOf(500.0, 500.0, 500.0)

    println("--------------------------------12------------------------------------")
    functions.index = 12

    val container1 = LinkedList<Double>()
    repeat(30) {
        batAlgorithm = OriginalBatAlgorithm(functions, n, Ngen, A, r, Qmin, Qmax, Lower, Upper)
        val bestValue = batAlgorithm.bestValue()
        println("origin best value $bestValue")
        container1.add(bestValue)
    }

    println()
    println("the worst   value is ${container1.max()}")
    println("the best    value is ${container1.min()}")
    println("the average value is ${container1.average()}")



    Lower = doubleArrayOf(-10.0, -10.0, -10.0)//  f11 griewank
    Upper = doubleArrayOf(10.0, 10.0, 10.0)


    println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~IMPROVED~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
    repeat(12) {

        functions.index = it
        val container = LinkedList<Double>()
        println("-----------------------------------$it----------------------------------------------")

        repeat(30) {
            batAlgorithm = ImprovedBatAlgorithm(functions, n, Ngen, A, r, Qmin, Qmax, Lower, Upper)

            val bestValue = batAlgorithm.bestValue()
            println("improved best value $bestValue")
            container.add(bestValue)

        }

        println()
        println("the worst   value is ${container.max()}")
        println("the best    value is ${container.min()}")
        println("the average value is ${container.average()}")
    }

    Lower = doubleArrayOf(-500.0, -500.0, -500.0)
    Upper = doubleArrayOf(500.0, 500.0, 500.0)
    println("--------------------------12---------------------------------")

    functions.index = 12
    val container2 = LinkedList<Double>()
    repeat(30) {
        batAlgorithm = ImprovedBatAlgorithm(functions, n, Ngen, A, r, Qmin, Qmax, Lower, Upper)
        val bestValue = batAlgorithm.bestValue()
        println("origin best value $bestValue")
        container2.add(bestValue)
    }

    println()
    println("the worst   value is ${container2.max()}")
    println("the best    value is ${container2.min()}")
    println("the average value is ${container2.average()}")

}
