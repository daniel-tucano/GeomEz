package space.elements

import extensions.*
import space.CoordinateSystem3D
import units.Angle
import utils.rotationMatrix3D
import org.ejml.simple.SimpleMatrix
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represent a point in 3D space written in the given coordinate system
 */
class Point3D(
    override var x: Double,
    override var y: Double,
    override var z: Double
): Entity3D {

    val distanceFromOrigin: Double
        get() = sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0))

    override val matrix: SimpleMatrix = SimpleMatrix(
        arrayOf(
            doubleArrayOf(x),
            doubleArrayOf(y),
            doubleArrayOf(z)
        )
    )

    /**
     * Matrix used for affine transformations that perform scaling and translation
     */
    override val affineMatrix: SimpleMatrix = SimpleMatrix(
        arrayOf(
            doubleArrayOf(x),
            doubleArrayOf(y),
            doubleArrayOf(z),
            doubleArrayOf(1.0)
        )
    )

    fun distanceBetween(point3D: Point3D): Double = (point3D - this).distanceFromOrigin

    /**
     * Rotate point in the anti-clockwise direction along the given axis
     */
    override fun rotate(axis: VectorialEntity3D, angle: Angle): Point3D {
        when (axis) {
            is Vector3D -> {
                /* First we need to translate the vector position to a point where the axis position
                act as the origin of our coordinate system  */
                val intermediatePosition = this - axis.position
                /* Now we need to find the rotation matrix */
                val rotationMatrix = rotationMatrix3D(axis.direction, angle)
                val (pX, pY, pZ) = rotationMatrix * intermediatePosition.matrix
                return Point3D(pX, pY, pZ) + axis.position
            }
            is Direction3D -> {
                /* Now we need to find the rotation matrix */
                val rotationMatrix = rotationMatrix3D(axis, angle)
                val (pX, pY, pZ) = rotationMatrix * this.matrix
                return Point3D(pX, pY, pZ)
            }
        }

    }

    /**
     * Describe point as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Point3D {
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
        return Point3D(-x,-y,-z)
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

        if (x != other.x) return false
        if (y != other.y) return false
        if (z != other.z) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + z.hashCode()
        return result
    }

}
