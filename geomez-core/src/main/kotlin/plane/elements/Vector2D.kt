package plane.elements

import extensions.times
import plane.CoordinateSystem2D
import space.CoordinateSystem3D
import space.elements.Vector3D
import space.elements.VectorialEntity3D
import units.Angle
import utils.rotationMatrix2D

class Vector2D(
    x: Double,
    y: Double,
    val position: Point2D = Point2D(0.0, 0.0)
) : VectorialEntity2D(x, y) {

    constructor(headPosition: Point2D, tailPosition: Point2D) : this(
        headPosition.x - tailPosition.x,
        headPosition.y - tailPosition.y,
        position = tailPosition
    )

    val direction: Direction2D
        get() = Direction2D(x, y)

    val headPosition: Point2D
        get() = position + Point2D(x, y)

    fun toVector3D(): Vector3D = Vector3D(x, y, 0.0)

    override infix fun cross(vectorialEntity2D: VectorialEntity2D): VectorialEntity3D {
        return Vector3D(
            0.0,
            0.0,
            this.x * vectorialEntity2D.y - this.y * vectorialEntity2D.x,
            position = position.toPoint3D()
        )
    }

    override fun rotate(angle: Angle): Vector2D {
        val rotationMatrix = rotationMatrix2D(angle)
        return Vector2D(
            headPosition = rotationMatrix * this.headPosition,
            tailPosition = rotationMatrix * this.position
        )
    }

    override fun rotate(centerOfRotation: Point2D, angle: Angle): Vector2D {
        val rotationMatrix = rotationMatrix2D(angle, centerOfRotation)
        return Vector2D(
            headPosition = rotationMatrix * this.headPosition,
            tailPosition = rotationMatrix * this.position
        )
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): Vector2D {
        val transformationMatrix = to.affineMatrix.invert() * asWrittenIn.affineMatrix
        return Vector2D(
            headPosition = transformationMatrix * this.headPosition,
            tailPosition = transformationMatrix * this.position
        )
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Vector3D {
        return this.toVector3D().changeBasis(asWrittenIn, to)
    }

    override fun unaryMinus(): VectorialEntity2D {
        return Vector2D(-x, -y, position)
    }
}