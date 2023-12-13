import java.io.File
import java.io.InputStream

fun main(args: Array<String>) {
    part1()
    part2()
}

fun part1(){
    val inputStream: InputStream = File("src/input.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    val processed = mutableListOf<MutableList<String>>()
    var partialNum = ""
    var answer = 0
    var tmp = mutableListOf<String>()
    inputString.split("\n").forEach {line ->
        tmp = mutableListOf<String>()
        line.trim().forEach { char ->
            if(char.isDigit()) {
                partialNum += char.toString()
            }
            else if (partialNum.isNotEmpty()) {
                (partialNum.indices).forEach {
                    tmp.add(partialNum)
                }
                tmp.add(char.toString())
                partialNum = ""
            }
            else {
                tmp.add(char.toString())
            }
        }
        if (partialNum.isNotEmpty()) {
            (partialNum.indices).forEach {
                tmp.add(partialNum)
            }
            partialNum = ""
        }
        processed.add(tmp.toMutableList())
    }
    var r = 0
    var c = 0
    var last = ""
    var x = 0
    val discarded = mutableSetOf(".")
    discarded.addAll((0..9).map { it.toString() })
    val direction = listOf(listOf(0, 1), listOf(1, 0), listOf(-1, 0), listOf(0, -1), listOf(-1, -1), listOf(1, 1), listOf(-1, 1), listOf(1, -1))
    processed.forEach {list ->
        val debug = mutableListOf<String>()
        list.forEach { str ->
            if (str.toIntOrNull() != null){
                if(direction.filter { lst ->
                        try{
                            processed[r + lst[0]][c + lst[1]].first().toString() !in discarded
                        }
                        catch (e: IndexOutOfBoundsException){
                            false
                        }
                    }.isNotEmpty()) {
                    if(str != last) {
                        debug.add(str)
                        x ++
                        answer += str.toInt()
                        last = str
                    }
                }
            }
            else {
                last = str
            }
            c ++
        }
        last = ""
        r ++
        c = 0
    }
    println(answer)
}

fun part2(){
    val inputStream: InputStream = File("src/input.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    val processed = mutableListOf<MutableList<String>>()
    var partialNum = ""
    var answer = 0
    var tmp = mutableListOf<String>()
    inputString.split("\n").forEach {line ->
        tmp = mutableListOf<String>()
        line.trim().forEach { char ->
            if(char.isDigit()) {
                partialNum += char.toString()
            }
            else if (partialNum.isNotEmpty()) {
                (partialNum.indices).forEach {
                    tmp.add(partialNum)
                }
                tmp.add(char.toString())
                partialNum = ""
            }
            else {
                tmp.add(char.toString())
            }
        }
        if (partialNum.isNotEmpty()) {
            (partialNum.indices).forEach {
                tmp.add(partialNum)
            }
            partialNum = ""
        }
        processed.add(tmp.toMutableList())
    }
    var r = 0
    var c = 0
    val discarded = mutableSetOf(".")
    discarded.addAll((0..9).map { it.toString() })
    val direction = listOf(listOf(0, 1), listOf(1, 0), listOf(-1, 0), listOf(0, -1), listOf(-1, -1), listOf(1, 1), listOf(-1, 1), listOf(1, -1))
    processed.forEach {list ->
        list.forEach { str ->
            if (str == "*"){
                var tmp2 = mutableListOf<Int>()
                direction.forEach { lst ->
                    try{
                        if(processed[r + lst[0]][c + lst[1]].toIntOrNull() != null){
                            tmp2.add(processed[r + lst[0]][c + lst[1]].toInt())
                        }
                    }
                    catch (e: IndexOutOfBoundsException){
                    }
                }
                tmp2 = tmp2.distinct().toMutableList()
                if (tmp2.size > 1){
                    answer += tmp2.reduce{ acc, i -> acc * i }
                }
            }
            c ++
        }
        r ++
        c = 0
    }
    println(answer)
}