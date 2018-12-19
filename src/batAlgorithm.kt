import java.util.*
import kotlin.collections.ArrayList

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
    var variable = 1.0
    var pulseRate = 3
    var loudness = 0.7
    var decay = 0.95

    private val random = Random()
    /**
     * dont set the initial value to big!
     * better initialize with the upper bound and lower bound
     *
     * narrow down initial locations
     */
    val randomLocation = {
        lowerBound + (upperBound - lowerBound) * random.nextDouble()
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
    private val objectives = LinkedList<Double>()
    private val window = ArrayList<DoubleArray>().apply {
        this.add(bestLocation)
    }
    val windowSize = 4
    private val posibility = LinkedList<Pair<Double,Double>>()

    fun initBats() {

        //双重for循环
        var i = 0
        var j = 0
        while (i < population) {
            while (j < dimension) {
                batPopulationLocation[i][j++] = randomLocation()
            }
            i++
        }

        //初始化bestLocation
        bestLocation = batPopulationLocation[0]
        objectives.add(objective(bestLocation))
        posibility.add(Pair(0.0,1.0 ))
        window.add(bestLocation)
    }
    
    abstract fun objective(xi:DoubleArray):Double

    fun startBats(generation: Int) {
        var copy = generation
        while (copy-- >= 0) {

            //iterate every bat!
            for (bat in batPopulationLocation.withIndex()) {

                //选择windowSize各种第r只蝙蝠开始跟随
                val r  = randomTarget()
                //修改目标bestValue
                bestValue = objectives[r]
                bestLocation = window[r]

                //the ith bat
                val i = bat.index
                val frequency = random.nextDouble() * ((2 + 2)) - 2
//            println(batPopulationFrequency[i])
                //update velocity the bat will fly to a random location
                for (temp in bat.value.withIndex()) {

                    if (bat.value.size > 3) break
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
                if (random.nextDouble() * 10 > pulseRate)
                    for (location in batPopulationLocation[i].withIndex()) {
                        //对最好的值周围进行一个扰动
                        val doubleArray = DoubleArray(dimension)
                        var counter = 0
                        while(counter < doubleArray.size){
//                            doubleArray[counter] = bestLocation[counter] + variable * loudness
                            //修改为跟随着某一个蝙蝠
                            doubleArray[counter] = window[r][counter] + variable * loudness
                            counter ++
                        }
                        batPopulationLocation[location.index] = doubleArray
                        variable *= decay
                    }

                val objectValue = objective(batPopulationLocation[i])
                if (objectValue < bestValue
                        && random.nextDouble()  < loudness) {

                    println("the best value of $objectValue")
                    loudness *= decay
                    pulseRate /= pulseRate
                    bestValue = objectValue
//                    bestLocation = batPopulationLocation[i]

                    //修改为改变这个跟随的蝙蝠的最好值
                     window[r] = batPopulationLocation[i]
                    bestLocation = batPopulationLocation[i]
                    //使用window观察结果
                    useWindow(batPopulationLocation[i])
                }

            }
        }
    }

    //使用一个随机数表示选择第几只蝙蝠开始跟随
    private fun randomTarget():Int{
        val double = random.nextDouble()
        var index = 0
        while(index < window.size){
            if(double < posibility[index].second && double >= posibility[index].first ){
                return index
            }
            index++
        }
        return index -1
    }


    //使用观察window 并且根据适应度找到 <= windowSize个蝙蝠的选择出来的比例
    private fun useWindow(location: DoubleArray){
        if(window.size < windowSize) {
            window.add(location)
        }else {
            //找出当前这个window中最差的值
            val worse = window.minBy {
                objective(it)
            }
            if (objective(location) < objective(worse!!)) {
                window.remove(worse)
                window.add(location)
            }
        }


        objectives.clear()
        window.forEach {it-> objectives.add(objective(it)) }

        val sum = objectives.sum()
        val weights = ArrayList<Double>()
        for(l in window.withIndex()){
            val thisIndex = l.index
            weights.add(objectives[thisIndex]/sum)
        }
        var counter = 0


        val flags = Array(window.size){true}
        val sortedWeight = LinkedList<Double>()
        weights.sortedDescending().forEach { sortedWeight.add(it) }
        //flag 计数 如果weights中所有都被改变 则也将会跳出循环
        var counterFlag = 0

        while(!sortedWeight.isEmpty() && counterFlag < window.size){
            var min = Double.MAX_VALUE
            val topValue = sortedWeight.poll()!!
            //第一次迭代找出最小值 的下标集合
            for(value in objectives.withIndex()){
                //flag 操作
                if(min >= value.value && flags[value.index]){
                    min =value.value
                    flags[value.index] = false
                }
            }
            //找出这些下标集合
            val minIndices = LinkedList<Int>()
            for(value in objectives.withIndex()){
                if(min == value.value)
                    minIndices.add(value.index)
            }

            for(index in minIndices){
                weights[index] = topValue
                counterFlag ++
            }
        }

        val sumWeight = weights.sum()
        for (weight in weights.withIndex()){
            weights[weight.index] = weight.value/sumWeight
        }

        //每次都需要刷新一次
        posibility.clear()

        var lastStart = 0.0
        var lastEnd   = weights[counter]
        while(counter + 1 < window.size){
            posibility.add(Pair(lastStart,lastEnd))
            lastStart = lastEnd
            lastEnd   += weights[counter + 1]
            counter ++
        }

        posibility.add(Pair(lastStart,1.0))


    }

    private fun simpleBounds(location:DoubleArray){
        for(i in location.withIndex()){
            if(i.value !in lLowerBound..uUpperBound){
                location[i.index] = (- lLowerBound + uUpperBound)*random.nextDouble() + lLowerBound
            }
        }
    }
}



