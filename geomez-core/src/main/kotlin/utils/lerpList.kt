package utils

fun lerpList(startValues: List<Double>, endValues: List<Double>, endFraction: Double): List<Double> {
    return startValues.mapIndexed { index, startValue -> lerp(startValue, endValues[index], endFraction) }
}