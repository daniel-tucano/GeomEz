package space.elements3D

import extensions.*
import space.CoordinateSystem3D
import units.Angle
import utils.rotationMatrix

/**
 * Represent a vector with arbitrary module and position in 3D space
 */
open class Vector3D(
    xComponent: Double,
    yComponent: Double,
    zComponent: Double,
    val position: Point3D = Point3D(0.0, 0.0, 0.0)
) : VectorialEntity3D(xComponent, yComponent, zComponent) {

    constructor(headPosition: Point3D, tailPosition: Point3D) : this(
        headPosition.x - tailPosition.x,
        headPosition.y - tailPosition.y,
        headPosition.z - tailPosition.z,
        position = tailPosition
    )

    val direction: Direction3D
        get() = Direction3D(x, y, z)

    val headPosition: Point3D
        get() = position + Point3D(x, y, z)

    /**
     * Rotate vector in the anti-clockwise direction along the given axis
     */
    override fun rotate(axis: VectorialEntity3D, angle: Angle): Vector3D {
        when (axis) {
            is Vector3D -> {
                /* First we need to translate the vector position to a point where the axis position
                act as the origin of our coordinate system  */
                val intermediatePosition = this.position - axis.position
                /* Now we need to find the rotation matrix */
                val rotationMatrix = rotationMatrix(axis.direction, angle)
                val (pX, pY, pZ) = rotationMatrix * intermediatePosition.matrix
                val (vX, vY, vZ) = rotationMatrix * this.matrix
                /* Then we return to our previous origin */
                val newPosition = Point3D(pX, pY, pZ) + axis.position
                return Vector3D(vX, vY, vZ, newPosition)
            }
            is Direction3D -> {
                val rotationMatrix = rotationMatrix(axis, angle)
                val (pX, pY, pZ) = rotationMatrix * this.position.matrix
                val (vX, vY, vZ) = rotationMatrix * this.matrix
                /* Then we return to our previous origin */
                val newPosition = Point3D(pX, pY, pZ)
                return Vector3D(vX, vY, vZ, newPosition)
            }
        }
    }

    /**
     * Describe point as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Vector3D {
        /**
         * 1 - We write the point vector as if it was written in the main coordinate system by multiplying its matrix
         * by the "asWrittenIn" matrix (asWrittenIn matrix describe its basis vectors in main coordinate system terms)
         * 2 - Then we write the resultant coordinates in the "to" coordinate system by multiplying it by the inverse of
         * the "to" matrix
         */
        val (newHeadX, newHeadY, newHeadZ) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.headPosition.affineMatrix
        val (newTailX, newTailY, newTailZ) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.position.affineMatrix
        val newHead = Point3D(newHeadX,newHeadY,newHeadZ)
        val newTail = Point3D(newTailX,newTailY,newTailZ)
        return Vector3D(headPosition = newHead, tailPosition = newTail)
    }

    override fun unaryMinus(): Vector3D {
        return Vector3D(-x, -y, -z, -position)
    }

}