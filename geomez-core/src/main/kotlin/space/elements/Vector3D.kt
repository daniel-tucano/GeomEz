package space.elements

import extensions.times
import space.CoordinateSystem3D
import units.Angle
import utils.rotationMatrix3D

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

    override fun rotate(axis: VectorialEntity3D, angle: Angle): Vector3D {
        val rotationMatrix = when (axis) {
            is Vector3D -> rotationMatrix3D(axis.direction, angle, axis.position)
            is Direction3D -> rotationMatrix3D(axis, angle)
        }
        return Vector3D(
            headPosition = rotationMatrix * this.headPosition,
            tailPosition = rotationMatrix * this.position
        )
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Vector3D {
        val transformationMatrix = to.affineMatrix.invert() * asWrittenIn.affineMatrix
        return Vector3D(
            headPosition = transformationMatrix * this.headPosition,
            tailPosition = transformationMatrix * this.position
        )
    }

    override fun unaryMinus(): Vector3D {
        return Vector3D(-x, -y, -z, position)
    }

}