package utils

/**
 * @param endFraction number between 0 and 1. When provided 0 result in startValue, interpolating
 * all the way to 1 when it result in endValue
 */
fun lerp(startValue: Double, endValue: Double, endFraction: Double): Double {
    return startValue * (1 - endFraction) + endValue * endFraction
}