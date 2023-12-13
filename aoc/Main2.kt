import java.io.File
import java.io.InputStream
import kotlin.math.max

//12 red cubes, 13 green cubes, and 14 blue cubes
fun main(args: Array<String>) {
    part1()
    part2()
}

fun part1(){
    val inputStream: InputStream = File("src/games.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    val limits = mapOf("red" to 12, "green" to 13, "blue" to 14)
    var answer = 0
    inputString.split("\n").forEach {
        val id = it.split(":")[0].split(" ")[1].trim().toInt()
        val data = it.split(":")[1]
        val throws = data.split(";")
        var goodThrows = 0
        throws.forEach {
            val control = mutableMapOf<String, Int>()
            val amounts = it.split(",")
            amounts.forEach { x ->
                val num = x.trim().split(" ")[0].trim().toInt()
                val type = x.trim().split(" ")[1].trim()
                control[type] = num
            }
            if(control.keys.filter { control[it]!! <= limits[it]!! }.count() == control.keys.count()){
                goodThrows ++
            }
        }
        if (goodThrows == throws.size) {
            answer += id
        }
    }
    println(answer)
}

fun part2(){
    val inputStream: InputStream = File("src/games.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    var answer = 0
    inputString.split("\n").forEach {
        val data = it.split(":")[1]
        val throws = data.split(";")
        val control = mutableMapOf<String, Int>()
        throws.forEach {
            val amounts = it.split(",")
            amounts.forEach { x ->
                val num = x.trim().split(" ")[0].trim().toInt()
                val type = x.trim().split(" ")[1].trim()
                if(type !in control) control[type] = num
                else control[type] = max(control[type]!!, num)
            }
        }
        answer += control.values.reduce { acc, i ->  acc * i }.toInt()
    }
    println(answer)
}