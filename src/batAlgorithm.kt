import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue

/***
 * the implementation of Bat Algorithm in Kotlin
 */


//the number of variables in function f : f(x1,x2,x3,.....,xn)
const val dimension = 3
//the population of bats
const val population = 500
//the iteration times of bats
const val generation = 1000
const val lowerBound = -5.0
const val upperBound = +5.0

const val uUpperBound = +500.0
const val lLowerBound = -500.0

@Suppress("UNUSED_EXPRESSION")
abstract class BatAlgorithm {
    private val random = Random()

    var variable = 1.0
    var pulseRate = random.nextDouble()
    var loudness = random.nextDouble()+1
    var decay = 0.95



    val randomLocation = {
        uUpperBound + (uUpperBound - lLowerBound) * random.nextDouble()
    }

    //初始化在范围中的一个下标
    val randomIndex ={
       random.nextInt().absoluteValue % population
    }



    private val batPopulationLocation = Array(population) { DoubleArray(dimension) }

    //2d array ith bat and the velocity in different direction
    private val batPopulationVelocity = Array(population) {
        DoubleArray(dimension) {
            random.nextDouble()
        }
    }

    var bestLocation = batPopulationLocation[0]
    var bestValue = 100000.0


    //假设先保存四只蝙蝠的位置
    private val windowSize = 4
    private val windows = LinkedList<Window>()

    //算法中的系数
    private val weightVariable = 0.9
    private var possibility = LinkedList<Double>()

    fun initBats() {

        //双重for循环
        var i = 0
        var j = 0
        while (i < population) {
            while (j < dimension) {
                val l = randomLocation();
                batPopulationLocation[i][j++] = l
            }
            j = 0
            i++
        }

        //初始化bestLocation
        bestLocation = batPopulationLocation[0]
        //初始windowSize个蝙蝠
        var counter = 0
        var lastEnd = (1.0/windowSize)
        while(counter +1 < windowSize){
            val location = batPopulationLocation[randomIndex()]
            val window = Window(
                    location =  location,
                    objectives = objective(location)
            )
            lastEnd = (1.0/windowSize)*(counter + 1)
            possibility.add(lastEnd)
            windows.add(window)
            counter++
        }
        val location = batPopulationLocation[randomIndex()]
        val window = Window(
                location =  location,
                objectives = objective(location)
        )
        windows.add(window)
        windows.sortedBy {
            it.objectives
        }
    }

    abstract fun objective(xi:DoubleArray):Double

    var copy = 0
    fun startBats(generation: Int) {
        copy = generation
        while (copy-- >= 0) {

            //iterate every bat!
            for (bat in batPopulationLocation.withIndex()) {

                //选择windowSize各种第r只蝙蝠开始跟随
                val r  = randomTarget()
                //修改目标bestValue
                bestValue = windows[r].objectives
                bestLocation = windows[r].location
//
//                println("the posibility")
//                println(possibility.toString())

                //the ith bat
                val i = bat.index
                val frequency = random.nextDouble() * ((2 ))
//            println(batPopulationFrequency[i])
                //update velocity the bat will fly to a random location
                for (temp in bat.value.withIndex()) {

                    val j = temp.index
                    val x = temp.value
                    batPopulationVelocity[i][j] =
                            (x - bestLocation[j]) * frequency + batPopulationVelocity[i][j]
                }


                //the velocity * timeSlice is a distance and the timeSlice is 1
                //store the location temporarily
                for (velocity in batPopulationVelocity[i].withIndex()) {
                    val j = velocity.index
                    batPopulationLocation[i][j] = batPopulationVelocity[i][j] * 1
                    +batPopulationLocation[i][j]
                }

                //进行修正坐标
                simpleBounds(batPopulationLocation[i])



                //using rate to convergence
                if (random.nextDouble() > pulseRate)
                    for (location in batPopulationLocation[i].withIndex()) {
                        //对最好的值周围进行一个扰动
                        val doubleArray = DoubleArray(dimension)
                        var counter = 0
                        while(counter < doubleArray.size){
//                            doubleArray[counter] = bestLocation[counter] + variable * loudness
                            //修改为跟随着某一个蝙蝠
//                            doubleArray[counter] = windows[r].location[counter] + variable * loudness
                            doubleArray[counter] = windows[r].location[counter] + (random.nextDouble()*2 - 1) * loudness
                            counter ++
                        }
                        batPopulationLocation[location.index] = doubleArray
//                        variable *= decay
                    }

                val objectValue = objective(batPopulationLocation[i])
                if (objectValue < bestValue
                        && random.nextDouble()  < loudness) {
                println("the object value of $objectValue")

//                    loudness *= decay
//                    pulseRate /= pulseRate

                    loudness *= 0.95
                    pulseRate *= Math.exp(-1 * 0.95 * (generation - copy).toDouble())
                    //bestValue = objectValue
//                    bestLocation = batPopulationLocation[i]

                    //修改为改变这个跟随的蝙蝠的最好值
                     windows[r].location = batPopulationLocation[i]
                    bestLocation = batPopulationLocation[i]
                    //使用window观察结果
                    useWindows(batPopulationLocation[i])
                }

            }
        }
            bestValue = windows.minBy { it.objectives }!!.objectives
    }

    private fun useWindows(location :DoubleArray){

        if(windows.size < windowSize){
        //这种一般不会出现
            //先初始化window的开始终结值都为0
            val window = Window(location = location, objectives = objective(location))
            windows.add(window)
        }else{
            val worst = windows.maxBy { it.objectives }!!
            val testingObjective = objective(location)
            if(testingObjective < worst.objectives){
                windows.remove(worst)
                windows.add(Window(location = location
                        ,objectives = testingObjective))
            }
        }
        windows.sortedBy { it.objectives }


        val weights = LinkedList<Double>()
        repeat(windows.size){
            weights.add(Math.pow(weightVariable,(it + 1).toDouble()))
        }

        val f = {
            (1/Math.E)*Math.pow(Math.E,((generation - copy)/generation).toDouble())
        }
        var counter= 0
        val temp = LinkedList<Double>()

        var end = possibility[counter]
        while(counter+1 < windowSize){

            end += (1 - end) * f() *weights[counter]
            temp.add(end)

            if(counter == windowSize -2 )
                break
            end = possibility[counter+1]
            counter ++
        }

        possibility = temp

    }

    //使用一个随机数表示选择第几只蝙蝠开始跟随
    private fun randomTarget():Int{
        val double = random.nextDouble()
        var index = 0
        while(index< windows.size-1){
            if(double <= possibility[index ]){
                return index
            }
            index++
        }
        return index
    }





    private fun simpleBounds(location:DoubleArray){
        for(i in location.withIndex()){
            if(i.value > upperBound)
                location[i.index] = upperBound
            if(i.value < lowerBound)
                location[i.index] = lowerBound
        }
    }
}



