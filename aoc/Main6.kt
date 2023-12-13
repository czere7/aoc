import kotlin.math.abs
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt

fun main(args: Array<String>) {
    part1()
    part2()
}

fun part1(){
    calc(listOf(41, 77, 70, 96), listOf(249, 1362, 1127, 1011))
}

fun part2(){
    calc(listOf(41777096), listOf(249136211271011))
}

fun calc(myTimes: List<Long>, myDists: List<Long>){
    var answ = 1
    (0 until myTimes.size).forEach {
        val max = myTimes[it]
        val d = myDists[it]
        //val amount = (0 .. max).map { x -> (max - x) * x }.filter { e -> e > d }.count()
        // quadratic formula
        val amount = abs(ceil((-max + sqrt(max.toDouble().pow(2) - 4 * d)) / (-2)).toInt() - ceil((-max - sqrt(max.toDouble().pow(2) - 4 * d)) / (-2)).toInt())
        answ *= amount
    }
    println(answ)
}
