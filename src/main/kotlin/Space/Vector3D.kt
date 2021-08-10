package Space

import Extensions.*
import Space.CoordinateSystem3D.Companion.MAIN_COORDINATE_SYSTEM
import Units.Angle
import Utils.rotationMatrix
import org.ejml.simple.SimpleMatrix
import kotlin.math.acos
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Represent a vector with arbitrary module and position in 3D space
 */
open class Vector3D (
    xComponent: Double,
    yComponent: Double,
    zComponent: Double,
    val position: Point3D = Point3D(0.0, 0.0, 0.0)
): Vector3DBase(xComponent,yComponent,zComponent) {

    val direction: Direction3D
        get() = Direction3D(xComponent, yComponent, zComponent)

    val headPosition: Point3D
        get() = position + Point3D(xComponent,yComponent,zComponent)

    /**
     * Rotate vector in the anti-clockwise direction along the given axis
     */
    fun rotate(axis: Vector3D, angle: Angle): Vector3D {
        /* First we need to translate the vector position to a point where the axis position
         act as the origin of our coordinate system  */
        val intermediatePosition = this.position - axis.position
        /* Now we need to find the rotation matrix */
        val rotationMatrix = rotationMatrix(axis.direction,angle)
        val (pX,pY,pZ) = rotationMatrix * intermediatePosition.matrix
        val (vX,vY,vZ) = rotationMatrix * this.matrix
        /* Then we return to our previous origin */
        val newPosition = Point3D(pX,pY,pZ) + axis.position
        return Vector3D(vX,vY,vZ,newPosition)
    }

    fun rotate(axis: Direction3D, angle: Angle): Vector3D {
        val rotationMatrix = rotationMatrix(axis,angle)
        val (pX,pY,pZ) = rotationMatrix * this.position.matrix
        val (vX,vY,vZ) = rotationMatrix * this.matrix
        /* Then we return to our previous origin */
        val newPosition = Point3D(pX,pY,pZ)
        return Vector3D(vX,vY,vZ,newPosition)
    }

    /**
     * Describe point as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Vector3D {
        /**
         * 1 - We write the point vector as if it was written in the main coordinate system by multiplying its matrix
         * by the "asWrittenIn" matrix (asWrittenIn matrix describe its basis vectors in main coordinate system terms)
         * 2 - Then we write the resultant coordinates in the "to" coordinate system by multiplying it by the inverse of
         * the "to" matrix
         */
        val (newX,newY,newZ) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.affineMatrix
        return Vector3D(newX,newY,newZ)
    }

    override fun unaryMinus(): Vector3D {
        return Vector3D(-xComponent,-yComponent,-zComponent,-position)
    }

}