package space.elements

import extensions.equalsDelta
import units.Angle
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Base class from which Vector3D and Direction3D can extend
 */
sealed class VectorialEntity3D (
    final override val x: Double,
    final override val y: Double,
    final override val z: Double,
): Entity3D {
    var module = sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0))

    fun toPoint3D(): Point3D = Point3D(x,y,z)

    infix fun angleBetween(vector3D: VectorialEntity3D): Angle.Radians {
        return Angle.Radians(acos(this dot vector3D / (this.module * vector3D.module)))
    }

//    Vector elementwise operations

    override operator fun plus(entity3D: Entity3D): Vector3D {
        return Vector3D(
            x + entity3D.x,
            y + entity3D.y,
            z + entity3D.z
        )
    }

    override operator fun minus(entity3D: Entity3D): Vector3D {
        return Vector3D(
            x - entity3D.x,
            y - entity3D.y,
            z - entity3D.z
        )
    }

    operator fun times(vector3D: Entity3D): Vector3D {
        return Vector3D(
            x * vector3D.x,
            y * vector3D.y,
            z * vector3D.z
        )
    }

    operator fun div(vector3D: Entity3D): Vector3D {
        return Vector3D(
            x / vector3D.x,
            y / vector3D.y,
            z / vector3D.z
        )
    }

    abstract override operator fun unaryMinus(): VectorialEntity3D

//    Vectorial operations

    infix fun dot(vector3D: VectorialEntity3D): Double {
        return x * vector3D.x +
                y * vector3D.y +
                z * vector3D.z
    }

    infix fun cross(vector3D: VectorialEntity3D): Vector3D {
        val (a1, a2, a3) = this
        val (b1, b2, b3) = vector3D

        return Vector3D(a2 * b3 - a3 * b2, a3 * b1 - a1 * b3, a1 * b2 - a2 * b1)
    }

//    Component get

    operator fun get(index: Int): Double {
        return when (index) {
            0 -> x
            1 -> y
            2 -> z
            else -> throw IllegalAccessError("Index out of bounds")
        }
    }

//    Scalar operations

    override operator fun plus(scalar: Double): Vector3D {
        return Vector3D(x + scalar, y + scalar, z + scalar)
    }

    override operator fun minus(scalar: Double): Vector3D {
        return Vector3D(x - scalar, y - scalar, z - scalar)
    }

    override operator fun times(scalar: Double): Vector3D {
        return Vector3D(x * scalar, y * scalar, z * scalar)
    }

    override operator fun div(scalar: Double): Vector3D {
        return Vector3D(x / scalar, y / scalar, z / scalar)
    }

//    Equals and HashCode

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VectorialEntity3D

        if (!x.equalsDelta(other.x)) return false
        if (!y.equalsDelta(other.y)) return false
        if (!z.equalsDelta(other.z)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }


}