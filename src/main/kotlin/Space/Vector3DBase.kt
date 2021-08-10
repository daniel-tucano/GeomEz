package Space

import Extensions.component1
import Extensions.component2
import Extensions.component3
import Extensions.times
import Units.Angle
import Utils.rotationMatrix
import org.ejml.simple.SimpleMatrix
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Base class from wich Vector3D and Direction3D can extend
 */
abstract class Vector3DBase (
    var xComponent: Double,
    var yComponent: Double,
    var zComponent: Double,
) {
    val module = sqrt(xComponent.pow(2.0) + yComponent.pow(2.0) + zComponent.pow(2.0))
    val matrix: SimpleMatrix = SimpleMatrix(
        arrayOf(
            doubleArrayOf(xComponent),
            doubleArrayOf(yComponent),
            doubleArrayOf(zComponent)
        )
    )

    infix fun angleBetween(vector3D: Vector3DBase): Angle.Radians {
        return Angle.Radians(acos(this dot vector3D / (this.module * vector3D.module)))
    }

//    Vector elementwise operations

    operator fun plus(vector3D: Vector3DBase): Vector3D {
        return Vector3D(
            xComponent + vector3D.xComponent,
            yComponent + vector3D.yComponent,
            zComponent + vector3D.zComponent
        )
    }

    operator fun minus(vector3D: Vector3DBase): Vector3D {
        return Vector3D(
            xComponent - vector3D.xComponent,
            yComponent - vector3D.yComponent,
            zComponent - vector3D.zComponent
        )
    }

    operator fun times(vector3D: Vector3DBase): Vector3D {
        return Vector3D(
            xComponent * vector3D.xComponent,
            yComponent * vector3D.yComponent,
            zComponent * vector3D.zComponent
        )
    }

    operator fun div(vector3D: Vector3DBase): Vector3D {
        return Vector3D(
            xComponent / vector3D.xComponent,
            yComponent / vector3D.yComponent,
            zComponent / vector3D.zComponent
        )
    }

    abstract operator fun unaryMinus(): Vector3DBase

//    Vectorial operations

    infix fun dot(vector3D: Vector3DBase): Double {
        return xComponent * vector3D.xComponent +
                yComponent * vector3D.yComponent +
                zComponent * vector3D.zComponent
    }

    infix fun cross(vector3D: Vector3DBase): Vector3D {
        val (a1, a2, a3) = this
        val (b1, b2, b3) = vector3D

        return Vector3D(a2 * b3 - a3 * b2, a3 * b1 - a1 * b3, a1 * b2 - a2 * b1)
    }

//    Component gets and sets

    operator fun get(index: Int): Double {
        return when (index) {
            0 -> xComponent
            1 -> yComponent
            2 -> zComponent
            else -> throw IllegalAccessError("Index out of bounds")
        }
    }

    operator fun set(index: Int, value: Double) {
        when (index) {
            0 -> xComponent = value
            1 -> yComponent = value
            2 -> zComponent = value
            else -> throw IllegalAccessError("Index out of bounds")
        }
    }

//    Scalar operations

    operator fun plus(scalar: Double): Vector3D {
        return Vector3D(xComponent + scalar, yComponent + scalar, zComponent + scalar)
    }

    operator fun minus(scalar: Double): Vector3D {
        return Vector3D(xComponent - scalar, yComponent - scalar, zComponent - scalar)
    }

    operator fun times(scalar: Double): Vector3D {
        return Vector3D(xComponent * scalar, yComponent * scalar, zComponent * scalar)
    }

    operator fun div(scalar: Double): Vector3D {
        return Vector3D(xComponent / scalar, yComponent / scalar, zComponent / scalar)
    }

//    Desctructin operations

    operator fun component1(): Double {
        return xComponent
    }

    operator fun component2(): Double {
        return yComponent
    }

    operator fun component3(): Double {
        return zComponent
    }

//    Equals and HashCode

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vector3D

        if (xComponent != other.xComponent) return false
        if (yComponent != other.yComponent) return false
        if (zComponent != other.zComponent) return false
        return true
    }

    override fun hashCode(): Int {
        var result = xComponent.hashCode()
        result = 31 * result + yComponent.hashCode()
        result = 31 * result + zComponent.hashCode()
        return result
    }


}