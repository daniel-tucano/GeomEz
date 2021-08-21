package plane.elements

import extensions.component1
import extensions.component2
import extensions.times
import plane.CoordinateSystem2D
import space.CoordinateSystem3D
import space.elements.Entity3D
import space.elements.Vector3D
import units.Angle
import utils.rotationMatrix2D

class Vector2D(
    x: Double,
    y: Double,
    val position: Point2D = Point2D(0.0,0.0)
) : VectorialEntity2D(x, y) {

    constructor(headPosition: Point2D, tailPosition: Point2D) : this(
        headPosition.x - tailPosition.x,
        headPosition.y - tailPosition.y,
        position = tailPosition
    )

    val  direction: Direction2D
        get() = Direction2D(x, y)

    val headPosition: Point2D
        get() = position + Point2D(x, y)

    fun toVector3D(): Vector3D = Vector3D(x,y,0.0)

    override fun rotate(centerOfRotation: Point2D, angle: Angle): Vector2D {
        val rotationMatrix = rotationMatrix2D(angle, centerOfRotation)
        val (newXHead, newYHead) = rotationMatrix * this.headPosition.affineMatrix
        val (newXTail, newYTail) = rotationMatrix * this.position.affineMatrix
        val newHead = Point2D(newXHead,newYHead)
        val newTail = Point2D(newXTail,newYTail)
        return Vector2D(headPosition = newHead, tailPosition = newTail)
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): Vector2D {
        val (newHeadX, newHeadY) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.affineMatrix
        val (newTailX, newTailY) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * Point2D(
            0.0,
            0.0
        ).affineMatrix
        val newHead = Point2D(newHeadX, newHeadY)
        val newTail = Point2D(newTailX, newTailY)
        return Vector2D(headPosition = newHead, tailPosition = newTail)
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Vector3D {
        return this.toVector3D().changeBasis(asWrittenIn, to)
    }

    override fun unaryMinus(): VectorialEntity2D {
        return Vector2D(-x, -y, position)
    }
}