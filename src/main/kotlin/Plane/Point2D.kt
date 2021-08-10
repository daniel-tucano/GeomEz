package Plane

import Extensions.component1
import Extensions.component2
import Extensions.component3
import Extensions.times
import Space.CoordinateSystem3D
import Space.Direction3D
import Space.Point3D
import Space.Vector3D
import Units.Angle
import Utils.rotationMatrix
import org.ejml.simple.SimpleMatrix
import java.lang.IllegalArgumentException
import kotlin.math.pow

data class Point2D(
    var x: Double,
    var y: Double,
    val coordinateSystem3D: CoordinateSystem3D = CoordinateSystem3D.MAIN_COORDINATE_SYSTEM
) {

    constructor(pair: Pair<Double, Double>) : this(pair.first, pair.second)

    val distanceFromOrigin: Double
        get() = kotlin.math.sqrt(x.pow(2) + y.pow(2))

    val toPositionVector3D
        get() = Vector3D(x,y,0.0)

    val matrix: SimpleMatrix = SimpleMatrix(
        arrayOf(
            doubleArrayOf(x),
            doubleArrayOf(y),
            doubleArrayOf(0.0)
        )
    )

    fun toPoint3D(z: Double = 0.0) = Point3D(x, y, z, coordinateSystem3D)

    fun distanceBetween(point2D: Point2D): Double = (this - point2D).distanceFromOrigin

    /**
     * Rotate point in the anti-clockwise direction along the given centerOfRotation
     */
    fun rotate(centerOfRotation: Point2D, angle: Angle): Point2D {
        /* First we need to translate the vector position to a point where the centerOfRotation position
        act as the origin of our coordinate system  */
        val intermediatePosition = this - centerOfRotation
        /* Now we need to find the rotation matrix */
        // TODO("Verify if using only main z direction is enough for all use cases")
        val rotationMatrix = rotationMatrix(Direction3D.MAIN_Z_DIRECTION, angle)
        val (pX, pY, _) = rotationMatrix * intermediatePosition.matrix
        return Point2D(pX, pY) + centerOfRotation
    }

    //    Point operations

    operator fun plus(point2D: Point2D): Point2D {
        if (this.coordinateSystem3D != point2D.coordinateSystem3D) throw IllegalArgumentException("Points coordinate systems must be the same")
        return Point2D(x + point2D.x, y + point2D.y, coordinateSystem3D)
    }

    operator fun minus(point2D: Point2D): Point2D {
        if (this.coordinateSystem3D != point2D.coordinateSystem3D) throw IllegalArgumentException("Points coordinate systems must be the same")
        return Point2D(x - point2D.x, y - point2D.y, coordinateSystem3D)
    }

    operator fun times(point2D: Point2D): Point2D {
        if (this.coordinateSystem3D != point2D.coordinateSystem3D) throw IllegalArgumentException("Points coordinate systems must be the same")
        return Point2D(x * point2D.x, y * point2D.y, coordinateSystem3D)
    }

    operator fun div(point2D: Point2D): Point2D {
        if (this.coordinateSystem3D != point2D.coordinateSystem3D) throw IllegalArgumentException("Points coordinate systems must be the same")
        return Point2D(x / point2D.x, y / point2D.y, coordinateSystem3D)
    }

//    Scalar operations

    operator fun plus(scalar: Double): Point2D {
        return Point2D(x + scalar, y + scalar, coordinateSystem3D)
    }

    operator fun minus(scalar: Double): Point2D {
        return Point2D(x - scalar, y - scalar, coordinateSystem3D)
    }

    operator fun times(scalar: Double): Point2D {
        return Point2D(x * scalar, y * scalar, coordinateSystem3D)
    }

    operator fun div(scalar: Double): Point2D {
        return Point2D(x / scalar, y / scalar, coordinateSystem3D)
    }

    operator fun unaryMinus(): Point2D {
        return Point2D(-x,-y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point2D

        if (x != other.x) return false
        if (y != other.y) return false
        if (coordinateSystem3D != other.coordinateSystem3D) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + coordinateSystem3D.hashCode()
        return result
    }

}