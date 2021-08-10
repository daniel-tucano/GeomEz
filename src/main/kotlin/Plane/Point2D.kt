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
    var y: Double
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

    /**
     * Matrix used for affine transformations that perform scaling and translation
     */
    val affineMatrix: SimpleMatrix = SimpleMatrix(
        arrayOf(
            doubleArrayOf(x),
            doubleArrayOf(y),
            doubleArrayOf(0.0),
            doubleArrayOf(1.0)
        )
    )

    fun toPoint3D(z: Double = 0.0) = Point3D(x, y, z)

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

    /**
     * Describe point as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Point3D {
        /**
         * 1 - We write the point vector as if it was written in the main coordinate system by multiplying its matrix
         * by the "asWrittenIn" matrix (asWrittenIn matrix describe its basis vectors in main coordinate system terms)
         * 2 - Then we write the resultant coordinates in the "to" coordinate system by multiplying it by the inverse of
         * the "to" matrix
         */
        val (newX,newY,newZ) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.affineMatrix
        return Point3D(newX,newY,newZ)
    }

    //    Point operations

    operator fun plus(point2D: Point2D): Point2D {
        return Point2D(x + point2D.x, y + point2D.y)
    }

    operator fun minus(point2D: Point2D): Point2D {
        return Point2D(x - point2D.x, y - point2D.y)
    }

    operator fun times(point2D: Point2D): Point2D {
        return Point2D(x * point2D.x, y * point2D.y)
    }

    operator fun div(point2D: Point2D): Point2D {
        return Point2D(x / point2D.x, y / point2D.y)
    }

//    Scalar operations

    operator fun plus(scalar: Double): Point2D {
        return Point2D(x + scalar, y + scalar)
    }

    operator fun minus(scalar: Double): Point2D {
        return Point2D(x - scalar, y - scalar)
    }

    operator fun times(scalar: Double): Point2D {
        return Point2D(x * scalar, y * scalar)
    }

    operator fun div(scalar: Double): Point2D {
        return Point2D(x / scalar, y / scalar)
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

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

}