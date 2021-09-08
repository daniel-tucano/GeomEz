package utils

import org.ejml.simple.SimpleMatrix

/**
 * Creates a list of Doubles equally spaced with "num" number of elements, witch includes "start" and "stop" elements
 */
fun linspace(start: Double, stop: Double, num: Int): List<Double> {

    if (num <= 1) throw IllegalArgumentException("num must be greater then 1")

    val array = MutableList(size = num) { 0.0 }
    val increment = (stop - start)/(num - 1)

    for (i in 0 until num) {
        array[i] = start + i * increment
    }

    return  array.toList()
}

fun factorial(number: Int): Long {
    if (number < 0L) {
        throw IllegalArgumentException("The number of which to calculate the factorial must be greater or equal to zero.")
    } else return when (number) {
        0 -> 1
        1 -> number.toLong()
        else -> number * factorial(number - 1)
    }
}

fun meshgrid(xValues: List<Double>, yValues: List<Double>): Pair<SimpleMatrix, SimpleMatrix> {
    val X = SimpleMatrix(
        yValues.map {
            xValues.toDoubleArray()
        }.toTypedArray()
    )

    val Y = SimpleMatrix(
        yValues.mapIndexed { index, _ ->
            DoubleArray(xValues.size) {yValues[index]}
        }.toTypedArray()
    )

    return X to Y
}