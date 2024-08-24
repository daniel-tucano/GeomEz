package space.elements

import extensions.equalsDelta
import extensions.times
import space.CoordinateSystem3D
import units.Angle
import utils.rotationMatrix3D
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Represent a point in 3D space written in the given coordinate system
 */
class Point3D(
    override var x: Double,
    override var y: Double,
    override var z: Double
) : Entity3D {

    val distanceFromOrigin: Double
        get() = sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0))

    fun asVector3D(): Vector3D = Vector3D(x, y, z)

    fun distanceBetween(point3D: Point3D): Double = (point3D - this).distanceFromOrigin

    /**
     * Rotate point in the anti-clockwise direction along the given axis
     */
    override fun rotate(axis: VectorialEntity3D, angle: Angle): Point3D {
        return when (axis) {
            is Vector3D -> rotationMatrix3D(axis.direction, angle, axis.position) * this
            is Direction3D -> rotationMatrix3D(axis, angle) * this
        }
    }

    /**
     * Describe point as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Point3D =
        to.affineMatrix.invert() * asWrittenIn.affineMatrix * this


    fun toCylindricalCoordinate(): CylindricalCoordinate {
        val radius = sqrt(distanceFromOrigin.pow(2.0) - this.z.pow(2.0))
        return CylindricalCoordinate(
            radius,
            Angle.Radians(
                cos(this.x/radius) * (if (sin(this.y/radius) < 0) -1.0 else 1.0)
            ),
            this.z
        )
    }

//    Point operations

    override operator fun plus(entity3D: Entity3D): Point3D {
        return Point3D(x + entity3D.x, y + entity3D.y, z + entity3D.z)
    }

    override operator fun minus(entity3D: Entity3D): Point3D {
        return Point3D(x - entity3D.x, y - entity3D.y, z - entity3D.z)
    }

    operator fun times(entity3D: Entity3D): Point3D {
        return Point3D(x * entity3D.x, y * entity3D.y, z * entity3D.z)
    }

    operator fun div(entity3D: Entity3D): Point3D {
        return Point3D(x / entity3D.x, y / entity3D.y, z / entity3D.z)
    }

    override operator fun unaryMinus(): Point3D {
        return Point3D(-x, -y, -z)
    }

//    Scalar operations

    override operator fun plus(scalar: Double): Point3D {
        return Point3D(x + scalar, y + scalar, z + scalar)
    }

    override operator fun minus(scalar: Double): Point3D {
        return Point3D(x - scalar, y - scalar, z - scalar)
    }

    override operator fun times(scalar: Double): Point3D {
        return Point3D(x * scalar, y * scalar, z * scalar)
    }

    override operator fun div(scalar: Double): Point3D {
        return Point3D(x / scalar, y / scalar, z / scalar)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point3D

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
