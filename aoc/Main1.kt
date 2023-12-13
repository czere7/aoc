import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
    part1()
    part2()
}

fun part1(){
    val digits = (0..9).map { it.toString() }.toHashSet()
    var answer = 0
    val filtered: MutableList<MutableList<Char>> = mutableListOf()
    val inputStream: InputStream = File("src/digits.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    inputString.split("\n").forEach {
        filtered.add(it.filter { e -> e.toString() in digits }.toMutableList())
    }
    filtered.forEach {
        answer += (it.first().toString() + it.last().toString()).toInt()
    }
    println(answer)
}

fun part2(){
    val digits = (0..9).map { it.toString() }.toHashSet()
    digits.addAll(listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine"))
    var answer = 0
    val filtered: MutableList<MutableList<String>> = mutableListOf()
    val inputStream: InputStream = File("src/digits.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    inputString.split("\n").forEach {
        var firstString = ""
        var firstIndex = 10000
        var lastString = ""
        var lastIndex = -1
        digits.forEach { d ->
            if (it.indexOf(d) > -1 && it.indexOf(d) < firstIndex){
                firstIndex = it.indexOf(d)
                firstString = returnDigit(d)
            }
            if (it.lastIndexOf(d) > lastIndex){
                lastIndex = it.lastIndexOf(d)
                lastString = returnDigit(d)
            }
        }
        println(firstString + lastString)
        filtered.add(mutableListOf(firstString, lastString))
    }
    filtered.forEach {
        answer += (it.first().toString() + it.last().toString()).toInt()
    }
    println(answer)
}

fun returnDigit(x: String): String{
    when(x){
        "one" -> return "1"
        "two" -> return "2"
        "three" -> return "3"
        "four" -> return "4"
        "five" -> return "5"
        "six" -> return "6"
        "seven" -> return "7"
        "eight" -> return "8"
        "nine" -> return "9"
        else -> return x
    }
}