package ru.tbank.education.school.lesson1

fun main() {
    println("Hello World")

    val a = 123
    val b: Short? = null
    var c = 12
    c++
    println("Hello $a")
    println("Hello $c")
    println("Hello $b")

    // val array = arrayOf(1, 2, 3)
    // val DoubleDimensionArray = Array(2) {
    //    Array(2) { 0 }
    // }
    // println(DoubleDimensionArray[0][0])
    for (i in 6 downTo 0 step 2) {
        println(i)
    }
}
