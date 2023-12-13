import java.io.File
import java.io.InputStream
import kotlin.math.pow

fun main(args: Array<String>) {
    part1()
    part2()
    val f: (st: String) -> Int = { st: String -> st.toInt() }
}

fun part1(){
    var score: Long = 0
    val inputStream: InputStream = File("src/cards.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    inputString.split("\n").forEach {
        val numbers = it.substring(it.indexOf(": ") + 1).split(" | ")
        val myNums = numbers[0].split(" ").map { it.trim()}.filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
        val winNums = numbers[1].split(" ").map { it.trim()}.filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
        if(myNums.intersect(winNums).isNotEmpty()){
            score += 2.0.pow(myNums.intersect(winNums).count().toDouble() - 1).toInt()
        }
    }
    println(score)
}

fun part2(){
    var index = 0
    val inputStream: InputStream = File("src/cards.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    val amount: MutableMap<Int, Long> = mutableMapOf()
    (0 until inputString.split("\n").size).forEach {
        amount[it] = 1
    }
    inputString.split("\n").forEach {
        val numbers = it.substring(it.indexOf(": ") + 1).split(" | ")
        val myNums = numbers[0].split(" ").map { it.trim()}.filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
        val winNums = numbers[1].split(" ").map { it.trim()}.filter { it.isNotEmpty() }.map { it.toInt() }.toSet()
        val numOfMatches = myNums.intersect(winNums).count()
        (1..numOfMatches).forEach{
            amount[index + it] = amount[index]!! + amount[index + it]!!
        }
        index ++
    }
    println(amount.values.sum())
}