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
    val position: Point3D = Point3D(0.0, 0.0, 0.0),
    val coordinateSystem3D: CoordinateSystem3D = MAIN_COORDINATE_SYSTEM
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

}