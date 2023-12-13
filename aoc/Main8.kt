import java.io.File
import java.io.InputStream
import java.lang.Integer.max
import java.lang.Math.pow

fun main(args: Array<String>) {
    part1()
    part2()
}

fun part1() {
    val inputStream: InputStream = File("src/main/map.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    val map = prepMap(inputString)
    var answer = 0
    var curPos = "AAA"
    while (true) {
        map["path"]!!.first.trim().forEach {
            answer++
            when(it){
                'L' -> curPos = map[curPos]!!.first
                'R' -> curPos = map[curPos]!!.second
            }
            if (curPos == "ZZZ") {
                println(answer)
                return
            }
        }
    }
}

fun part2(){
    val inputStream: InputStream = File("src/main/map.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    val map = prepMap(inputString)
    var starts = mutableListOf<String>()
    var answers = mutableListOf<Int>()
    map.keys.forEach {
        if (it.last() == 'A'){
            starts.add(it)
        }
    }
    starts.forEach {
        var curPos = it
        var answer = 0
        var go = true
        while (go) {
            map["path"]!!.first.trim().forEach {
                answer++
                when (it) {
                    'L' -> curPos = map[curPos]!!.first
                    'R' -> curPos = map[curPos]!!.second
                }
                if (curPos.last() == 'Z') {
                    answers.add(answer)
                    go = false
                }
            }
        }
    }
    println(answers)
    val factors: MutableMap<Int, Int> = mutableMapOf()
    answers.forEach {
        val f = primeFactors(it)
        val distinctPrimes = f.toSet().toList()
        distinctPrimes.forEach {i ->
            if (i in factors.keys){
                factors[i] = max(factors[i]!!, f.count { e -> e == i })
            }
            else{
                factors[i] = f.count { e -> e == i }
            }
        }
    }
    var a = 1L
    factors.forEach { a *= pow(it.key.toDouble(), it.value.toDouble()).toLong() }
    println(a)
}

// https://www.geeksforgeeks.org/print-all-prime-factors-of-a-given-number/
fun primeFactors(n0: Int): MutableList<Int> {
    var n = n0
    val ret = mutableListOf<Int>()
    while (n % 2 == 0) {
        ret.add(2)
        n /= 2
    }
    var i = 3
    while (i <= Math.sqrt(n.toDouble())) {
        while (n % i == 0) {
                ret.add(i)
                n /= i
            }
            i += 2
        }
    if (n > 2) ret.add(n)
    return ret
}

fun prepMap(input: String): MutableMap<String, Pair<String, String>>{
    val ret = mutableMapOf<String, Pair<String, String>>()
    val splitInput = input.split("\n").toMutableList()
    ret["path"] = Pair(splitInput.removeAt(0), "")
    splitInput.removeAt(0)
    splitInput.forEach {
        ret[it.substring(0, 3)] = Pair(it.substring(7, 10), it.substring(12, 15))
    }
    return ret
}