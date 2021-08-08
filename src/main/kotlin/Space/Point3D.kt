package Space

import Extensions.component1
import Extensions.component2
import Extensions.component3
import Extensions.times
import Plane.Point2D
import Units.Angle
import Utils.rotationMatrix
import org.ejml.simple.SimpleMatrix
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represent a point in 3D space written in the given coordinate system
 */
class Point3D(
    var x: Double,
    var y: Double,
    var z: Double,
    val coordinateSystem3D: CoordinateSystem3D =  CoordinateSystem3D.MAIN_COORDINATE_SYSTEM
) {

    val distanceFromOrigin: Double
        get() = sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0))

    val matrix: SimpleMatrix = SimpleMatrix(
        arrayOf(
            doubleArrayOf(x),
            doubleArrayOf(y),
            doubleArrayOf(z)
        )
    )

    fun distanceBetween(point3D: Point3D): Double = (point3D - this).distanceFromOrigin

    /**
     * Rotate point in the anti-clockwise direction along the given axis
     */
    fun rotate (axis: Vector3D, angle: Angle): Point3D {
        /* First we need to translate the vector position to a point where the axis position
        act as the origin of our coordinate system  */
        val intermediatePosition = this - axis.position
        /* Now we need to find the rotation matrix */
        val rotationMatrix = rotationMatrix(axis.direction,angle)
        val (pX,pY,pZ) = rotationMatrix * intermediatePosition.matrix
        return Point3D(pX,pY,pZ) + axis.position
    }

//    Point operations

    operator fun plus(point3D: Point3D): Point3D {
        return Point3D(x + point3D.x, y + point3D.y, z + point3D.z)
    }

    operator fun minus(point3D: Point3D): Point3D {
        return Point3D(x - point3D.x, y - point3D.y, z - point3D.z)
    }

    operator fun times(point3D: Point3D): Point3D {
        return Point3D(x * point3D.x, y * point3D.y, z * point3D.z)
    }

    operator fun div(point3D: Point3D): Point3D {
        return Point3D(x / point3D.x, y / point3D.y, z / point3D.z)
    }

//    Scalar operations

    operator fun plus(scalar: Double): Point3D {
        return Point3D(x + scalar, y + scalar, z + scalar)
    }

    operator fun minus(scalar: Double): Point3D {
        return Point3D(x - scalar, y - scalar, z - scalar)
    }

    operator fun times(scalar: Double): Point3D {
        return Point3D(x * scalar, y * scalar, z * scalar)
    }

    operator fun div(scalar: Double): Point3D {
        return Point3D(x / scalar, y / scalar, z / scalar)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point3D

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false
        if (coordinateSystem3D != other.coordinateSystem3D) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        result = 31 * result + coordinateSystem3D.hashCode()
        return result
    }

}