package plane.elements


import extensions.equalsDelta
import space.elements.Vector3D
import space.elements.VectorialEntity3D
import units.Angle
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

sealed class VectorialEntity2D(
    final override val x: Double,
    final override val y: Double,
) : Entity2D {
    val module = sqrt(x.pow(2.0) + y.pow(2.0))

    infix fun angleBetween(vector3D: VectorialEntity2D): Angle.Radians {
        return Angle.Radians(acos(this dot vector3D / (this.module * vector3D.module)))
    }

//    Vector elementwise operations

    override operator fun plus(entity2D: Entity2D): Vector2D {
        return Vector2D(
            x + entity2D.x,
            y + entity2D.y
        )
    }

    override operator fun minus(entity2D: Entity2D): Vector2D {
        return Vector2D(
            x - entity2D.x,
            y - entity2D.y
        )
    }

    operator fun times(vector3D: Entity2D): Vector2D {
        return Vector2D(
            x * vector3D.x,
            y * vector3D.y
        )
    }

    operator fun div(vector3D: Entity2D): Vector2D {
        return Vector2D(
            x / vector3D.x,
            y / vector3D.y
        )
    }

    abstract override operator fun unaryMinus(): VectorialEntity2D

//    Vectorial operations

    infix fun dot(vector3D: VectorialEntity2D): Double {
        return x * vector3D.x +
                y * vector3D.y
    }

    infix fun cross(vector2D: VectorialEntity2D): Vector3D {
        val (a1, a2) = this
        val (b1, b2) = vector2D

        return Vector3D(0.0, 0.0, a1 * b2 - a2 * b1)
    }

//    Component get

    operator fun get(index: Int): Double {
        return when (index) {
            0 -> x
            1 -> y
            else -> throw IllegalAccessError("Index out of bounds")
        }
    }

//    Scalar operations

    override operator fun plus(scalar: Double): Vector2D {
        return Vector2D(x + scalar, y + scalar)
    }

    override operator fun minus(scalar: Double): Vector2D {
        return Vector2D(x - scalar, y - scalar)
    }

    override operator fun times(scalar: Double): Vector2D {
        return Vector2D(x * scalar, y * scalar)
    }

    override operator fun div(scalar: Double): Vector2D {
        return Vector2D(x / scalar, y / scalar)
    }

//    Equals and HashCode

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VectorialEntity2D

        if (!x.equalsDelta(other.x)) return false
        if (!y.equalsDelta(other.y)) return false
        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}
