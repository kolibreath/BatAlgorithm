import java.util.*

class TestAlgorithm(private val i:Int) :BatAlgorithm() {


    override fun objective(xi: DoubleArray): Double {
        val function = Functions()
        return function.runAll(xi,dimension,i)
    }
}

fun main(args:Array<String>) {


    lateinit var algrithm :TestAlgorithm
    val list = listOf("sphere", "schwefel 2.22", "schwefel 1.2",
            "schwefel 2.21", "rosenBrock", "step", "quarticWithNoise", "rastrign", "ackley"
            , "griewank", "penalized", "penalized2")

//    list.forEachIndexed { index, s ->
//
//        //收藏进行30次这个运算的最好值！
//        val container = LinkedList<Double>()
//        repeat(1) {
//            algrithm = TestAlgorithm(index)
//            algrithm.initBats()
//            algrithm.startBats(generation)
//            println("the $it th value of$s is ${algrithm.bestValue} ")
//            container.add(algrithm.bestValue)
//        }
//        println("the best value of $s is "+container.min())
//        println("the worst value of $s is ${container.max()}")
//        println("the average value of $s is ${container.average()}")
//    }


    algrithm = TestAlgorithm(12)
    algrithm.initBats()
    val co = LinkedList<Double>()
    repeat(1){
        algrithm.startBats(generation)
//        println("the $it th value of schewel 2.6 is ${algrithm.bestValue} ")
        co.add(algrithm.bestValue)
    }
    println("the best value of schwefel 2.6 is ${co.min()}")
    println("the worst value of schwefel 2.6 is ${co.max()}")
    println("the average value of schwefel 2.6 is ${co.average()}")


}