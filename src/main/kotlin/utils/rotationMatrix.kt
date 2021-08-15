package utils

import space.elements3D.VectorialEntity3D
import units.Angle
import org.ejml.simple.SimpleMatrix
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin

fun rotationMatrix(axis: VectorialEntity3D, angle: Angle): SimpleMatrix {
    val(uX,uY,uZ) = axis

    val theta = when(angle) {
        is Angle.Radians -> angle.value
        is Angle.Degrees -> angle.toRadians().value
    }

    return SimpleMatrix(
        arrayOf(
            doubleArrayOf(cos(theta) + uX.pow(2) * (1 - cos(theta)), uX * uY * (1 - cos(theta)) - uZ * sin(theta), uX * uZ * (1 - cos(theta)) + uY * sin(theta)),
            doubleArrayOf(uY * uX * (1 - cos(theta)) + uZ * sin(theta), cos(theta) + uY.pow(2) * (1 - cos(theta)), uY * uZ * (1 - cos(theta)) - uX * sin(theta)),
            doubleArrayOf(uZ * uX * (1 - cos(theta)) - uY * sin(theta), uZ * uY * (1 - cos(theta)) + uX * sin(theta), cos(theta) + uZ.pow(2) * (1 - cos(theta)))
        )
    )
}