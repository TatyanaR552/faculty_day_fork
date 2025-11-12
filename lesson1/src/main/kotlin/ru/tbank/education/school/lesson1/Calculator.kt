package ru.tbank.education.school.lesson1

import kotlin.math.*
import java.math.BigDecimal
import java.math.MathContext

/**
 * Метод для вычисления простых арифметических операций.
 */
fun calculate(a: Double, b: Double, operation: OperationType = OperationType.ADD): Double? {
    return when (operation) {
                OperationType.ADD ->  a + b
                OperationType.MULTIPLY -> a * b
                OperationType.SUBTRACT -> a - b
                OperationType.DIVIDE -> if (b != 0.0) a / b else null

    }
}

/**
 * Функция вычисления выражения, представленного строкой
 * @return результат вычисления строки или null, если вычисление невозможно
 * @sample "5 * 2".calculate()
 */
@Suppress("ReturnCount")
fun String.calculate(): Double? {
    val x = this.replace(" ", "")
    return try {
        calculateBigDecimal(x)?.toDouble()
    } catch (_: Exception) {
        null
    }
}

private fun matchBracket(x: String, openInd: Int): Int? {
    var count = 1
    for (i in openInd + 1 until x.length) {
        when (x[i]) {
            '(' -> count++
            ')' -> {
                count--
                if (count == 0)
                    return i
            }
        }
    }
    return null
}

private fun findOp(x: String, mathContext: MathContext): BigDecimal? {
    for (i in x.indices) {
        when (x[i]) {
            '+' -> {
                val left = BigDecimal(x.take(i), mathContext)
                val right = BigDecimal(x.drop(i + 1), mathContext)
                return left + right
            }
            '-' -> {
                val left = BigDecimal(x.take(i), mathContext)
                val right = BigDecimal(x.drop(i + 1), mathContext)
                return left - right
            }
            '*' -> {
                val left = BigDecimal(x.take(i), mathContext)
                val right = BigDecimal(x.drop(i + 1), mathContext)
                return left * right
            }
            '/' -> {
                val left = BigDecimal(x.take(i), mathContext)
                val right = BigDecimal(x.drop(i + 1), mathContext)
                if (right == BigDecimal.ZERO)
                    return null
                return left / right
            }
        }
    }
    return try {
        BigDecimal(x, mathContext)
    } catch (_: Exception) {
        null
    }
}

private fun mathFunc(y: String, mathContext: MathContext): String? {
    var z = y
    val func = listOf("sin", "cos", "tan", "log", "sqrt")
    var flag = true
    while (flag) {
        flag = false
        for (f in func) {
            if (z.startsWith(f) && z.length > f.length && z[f.length] == '(') {
                val closeInd = matchBracket(z, f.length) ?: return null
                val inside = z.substring(f.length + 1, closeInd)
                val arg = findOp(inside, mathContext) ?: return null
                val res = when (f) {
                    "sin" -> sin(arg.toDouble()).toBigDecimal(mathContext)
                    "cos" -> cos(arg.toDouble()).toBigDecimal(mathContext)
                    "tan" -> tan(arg.toDouble()).toBigDecimal(mathContext)
                    "log" -> log10(arg.toDouble()).toBigDecimal(mathContext)
                    "sqrt" -> {
                        if (arg < BigDecimal.ZERO) return null
                        sqrt(arg.toDouble()).toBigDecimal(mathContext)
                    }
                    else -> return null
                }
                z = res.toString() + z.substring(closeInd + 1)
                flag = true
                break
            }
        }
    }
    return z
}

private fun calculateBigDecimal(x: String): BigDecimal? {
    var y = x
    val mathContext = MathContext(100)
    y = mathFunc(y, mathContext) ?: return null
    while (y.contains('(')) {
        val open = y.indexOf('(')
        val close = matchBracket(y, open) ?: return null
        val inside = y.substring(open + 1, close)
        val result = calculateBigDecimal(inside) ?: return null
        y = y.take(open) + result.toString() + y.drop(close + 1)
        y = mathFunc(y, mathContext) ?: return null
    }
    return findOp(y, mathContext)
}

fun main() {

}
