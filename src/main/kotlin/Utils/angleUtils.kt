package Units

fun cos(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.cos(angle.value)
    is Angle.Degrees -> kotlin.math.cos(angle.toRadians().value)
}

fun sin(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.sin(angle.value)
    is Angle.Degrees -> kotlin.math.sin(angle.toRadians().value)
}

fun tan(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.tan(angle.value)
    is Angle.Degrees -> kotlin.math.tan(angle.toRadians().value)
}

fun acos(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.acos(angle.value)
    is Angle.Degrees -> kotlin.math.acos(angle.toRadians().value)
}
fun asin(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.asin(angle.value)
    is Angle.Degrees -> kotlin.math.asin(angle.toRadians().value)
}

fun atan(angle: Angle): Double = when (angle) {
    is Angle.Radians -> kotlin.math.atan(angle.value)
    is Angle.Degrees -> kotlin.math.atan(angle.toRadians().value)
}