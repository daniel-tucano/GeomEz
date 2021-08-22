package plane.elements


import extensions.equalsDelta
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

    infix fun angleBetween(vector2D: VectorialEntity2D): Angle.Radians {
        return Angle.Radians(acos(this dot vector2D / (this.module * vector2D.module)))
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

    operator fun times(vector2D: Entity2D): Vector2D {
        return Vector2D(
            x * vector2D.x,
            y * vector2D.y
        )
    }

    operator fun div(vector2D: Entity2D): Vector2D {
        return Vector2D(
            x / vector2D.x,
            y / vector2D.y
        )
    }

    abstract override operator fun unaryMinus(): VectorialEntity2D

//    Vectorial operations

    infix fun dot(vector2D: VectorialEntity2D): Double = x * vector2D.x + y * vector2D.y

    abstract infix fun cross(vectorialEntity2D: VectorialEntity2D): VectorialEntity3D

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
