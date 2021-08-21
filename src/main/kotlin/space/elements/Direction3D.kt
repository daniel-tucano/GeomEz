package space.elements

import extensions.component1
import extensions.component2
import extensions.component3
import extensions.times
import plane.elements.Direction2D
import space.CoordinateSystem3D
import units.Angle
import utils.rotationMatrix3D
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represent a direction (unit vector at the origin) in 3D space
 */
class Direction3D(x: Double, y: Double, z: Double) :
    VectorialEntity3D(
        x / sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0)),
        y / sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0)),
        z / sqrt(x.pow(2.0) + y.pow(2.0) + z.pow(2.0))
    ) {

    companion object {
        val MAIN_X_DIRECTION = Direction3D(1.0, 0.0, 0.0)
        val MAIN_Y_DIRECTION = Direction3D(0.0, 1.0, 0.0)
        val MAIN_Z_DIRECTION = Direction3D(0.0, 0.0, 1.0)
    }


    /**
     * Rotate vector in the anti-clockwise direction along the given axis
     */
    override fun rotate(axis: VectorialEntity3D, angle: Angle): VectorialEntity3D {
        return when (axis) {
            is Vector3D -> {
                /* First we need to translate the vector position to a point where the axis position
                act as the origin of our coordinate system  */
                val intermediatePosition = -axis.position
                /* Now we need to find the rotation matrix */
                val rotationMatrix = rotationMatrix3D(axis.direction, angle)
                val (pX, pY, pZ) = rotationMatrix * intermediatePosition.matrix
                val (vX, vY, vZ) = rotationMatrix * this.matrix
                /* Then we return to our previous origin */
                val newPosition = Point3D(pX, pY, pZ) + axis.position
                Vector3D(vX, vY, vZ, newPosition)
            }
            is Direction3D -> {
                val rotationMatrix = rotationMatrix3D(axis, angle)
                val (vX, vY, vZ) = rotationMatrix * this.matrix
                Direction3D(vX, vY, vZ)
            }
        }
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Vector3D {
        /**
         * 1 - We write the point vector as if it was written in the main coordinate system by multiplying its matrix
         * by the "asWrittenIn" matrix (asWrittenIn matrix describe its basis vectors in main coordinate system terms)
         * 2 - Then we write the resultant coordinates in the "to" coordinate system by multiplying it by the inverse of
         * the "to" matrix
         */
        val (newHeadX, newHeadY, newHeadZ) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.affineMatrix
        val (newTailX, newTailY, newTailZ) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * Point3D(
            0.0,
            0.0,
            0.0
        ).affineMatrix
        val newHead = Point3D(newHeadX, newHeadY, newHeadZ)
        val newTail = Point3D(newTailX, newTailY, newTailZ)
        return Vector3D(headPosition = newHead, tailPosition = newTail)
    }

    infix fun cross(direction: Direction3D): Direction3D {
        val (a1, a2, a3) = this
        val (b1, b2, b3) = direction

        return Direction3D(a2 * b3 - a3 * b2, a3 * b1 - a1 * b3, a1 * b2 - a2 * b1)
    }

    /**
     * Treat a 2D direction as a vector in XY plane (z component = 0)
     */
    infix fun cross(direction: Direction2D): Direction3D {
        val (a1, a2, a3) = this
        val (b1, b2) = direction

        return Direction3D( - a3 * b2, a3 * b1, a1 * b2 - a2 * b1)
    }

    override fun unaryMinus(): Direction3D {
        return Direction3D(-x, -y, -z)
    }
}