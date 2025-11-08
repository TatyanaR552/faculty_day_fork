package ru.tbank.education.school.lesson1

/**
 * Сумма четных чисел.
 */
fun sumEvenNumbers(numbers: IntArray): Int {
    var sum = 0
    for (num in numbers) {
        if (num % 2 == 0)
            sum += num
    }
    return sum
}

fun main() {
    val x = sumEvenNumbers(intArrayOf(1, 2, 3, 4, 5))
    println("Result: $x")
}
