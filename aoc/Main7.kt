import java.io.File
import java.io.InputStream
import java.lang.NullPointerException

fun main(args: Array<String>) {
    part1()
    part2()
}

fun part1(){
    var winnings = 0
    var rank = 1
    val inputStream: InputStream = File("src/main/cards.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    val values = mapOf("A" to 13, "K" to 12, "Q" to 11, "J" to 10, "T" to 9, "9" to 8, "8" to 7, "7" to 6, "6" to 5, "5" to 4, "4" to 3, "3" to 2, "2" to 1)
    val comparator = Comparator{ str1 : String, str2 : String -> compare(str1, str2, values) }
    val map = sortIntoMap(inputString)
    map.values.forEach {
        val tmp = it.sortedWith(comparator)
        it.clear()
        it.addAll(tmp)
    }
    listOf("hc", "1p", "2p", "3k", "fh", "4k", "5k").forEach {
        map[it]?.forEach { hand ->
            winnings += rank * hand.split(" ")[1].trim().toInt()
            rank ++
        }
    }
    println(winnings)
}

fun part2(){
    var winnings = 0
    var rank = 1
    val inputStream: InputStream = File("src/main/cards.txt").inputStream()
    val inputString = inputStream.bufferedReader().use { it.readText() }
    val values = mapOf("A" to 13, "K" to 12, "Q" to 11, "J" to 0, "T" to 9, "9" to 8, "8" to 7, "7" to 6, "6" to 5, "5" to 4, "4" to 3, "3" to 2, "2" to 1)
    val comparator = Comparator{ str1 : String, str2 : String -> compare(str1, str2, values) }
    val map = sortIntoMap(inputString, 2)
    map.values.forEach {
        val tmp = it.sortedWith(comparator)
        it.clear()
        it.addAll(tmp)
    }
    listOf("hc", "1p", "2p", "3k", "fh", "4k", "5k").forEach {
        map[it]?.forEach { hand ->
            winnings += rank * hand.split(" ")[1].trim().toInt()
            rank ++
        }
    }
    println(winnings)
}

fun compare(str1 : String, str2 : String, valuesOfCards: Map<String, Int>): Int{
    for(i in str1.indices){
        try {
            if (valuesOfCards[str1[i].toString()]!! - valuesOfCards[str2[i].toString()]!! != 0){
                return valuesOfCards[str1[i].toString()]!! - valuesOfCards[str2[i].toString()]!!
            }
        } catch (e: NullPointerException){
            return 0
        }

    }
    return 0
}

fun sortIntoMap(input: String, part: Int = 1): MutableMap<String, MutableList<String>>{
    val ret = mutableMapOf<String, MutableList<String>>()
    input.split("\n").forEach {
        val cat = getCategory(it.split(" ")[0], part)
        if (cat in ret)
            ret[cat]?.add(it)
        else
            ret[cat] = mutableListOf(it)
    }
    return ret
}

fun getCategory(input: String, part: Int = 1): String{
    val amounts = getAmounts(input, part)
    if (amounts.size == 1) return "5k"
    if (4 in amounts) return "4k"
    if (2 in amounts && 3 in amounts) return "fh"
    if (3 in amounts) return "3k"
    if (amounts.count { it == 2 } == 2) return "2p"
    if (2 in amounts) return "1p"
    if (amounts.size == 5) return "hc"
    throw Exception("$input did not fit anz category")
}

fun getAmounts(input: String, part: Int = 1): MutableList<Int> {
    val amounts = mutableListOf<Int>()
    input.split("").toSet().filter { it.isNotEmpty() }.forEach {
        amounts.add(input.count { l -> l.toString() == it })
    }
    if (part != 1 && amounts.size != 1) {
        val jAmount = input.count { it.toString() == "J" }
        amounts.remove(jAmount)
        amounts.sortDescending()
        amounts[0] += jAmount
    }
    return amounts
}
