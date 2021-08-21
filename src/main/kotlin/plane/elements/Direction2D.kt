package plane.elements

import extensions.component1
import extensions.component2
import extensions.component3
import extensions.times
import plane.CoordinateSystem2D
import space.CoordinateSystem3D
import space.elements.Direction3D
import space.elements.Point3D
import space.elements.Vector3D
import space.elements.VectorialEntity3D
import units.Angle
import utils.rotationMatrix2D
import kotlin.math.sqrt

class Direction2D(
    x: Double,
    y: Double
) : VectorialEntity2D(x / sqrt(x * x + y * y), y / sqrt(x * x + y * y)) {

    companion object {
        val MAIN_X_DIRECTION = Direction2D(1.0, 0.0)
        val MAIN_Y_DIRECTION = Direction2D(0.0, 1.0)
    }

    fun toPoint3D(): Point3D = Point3D(x,y,0.0)

    /**
     * @return Anti-clockwise perpendicular direction
     */
    fun perpendicularDirection(): Direction2D = Direction2D( -y, x)

    override fun unaryMinus(): Direction2D {
        return Direction2D(-x, -y)
    }

    fun rotate(angle: Angle): Direction2D {
        val rotationMatrix = rotationMatrix2D(angle)
        val (vX, vY) = rotationMatrix * this.matrix
        return Direction2D(vX, vY)
    }

    override fun rotate(centerOfRotation: Point2D, angle: Angle): Entity2D {
        val rotationMatrix = rotationMatrix2D(angle, centerOfRotation)
        val (newHeadX, newHeadY) = rotationMatrix * this.affineMatrix
        val (newTailX, newTailY) = rotationMatrix * Point2D(
            0.0,
            0.0
        ).affineMatrix
        val newHead = Point2D(newHeadX, newHeadY)
        val newTail = Point2D(newTailX, newTailY)
        return Vector2D(headPosition = newHead, tailPosition = newTail)
    }

    /**
     * When the two Coordinate Systems shares the same origin the result it's a direction, otherwise it's a vector
     */
    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): VectorialEntity2D {
        return when (asWrittenIn.origin) {
            to.origin -> {
                val (newXComponent, newYComponent) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.affineMatrix
                Direction2D(newXComponent, newYComponent)
            }
            else -> {
                val (newHeadX, newHeadY) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.affineMatrix
                val (newTailX, newTailY) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * Point2D(
                    0.0,
                    0.0
                ).affineMatrix
                val newHead = Point2D(newHeadX, newHeadY)
                val newTail = Point2D(newTailX, newTailY)
                Vector2D(headPosition = newHead, tailPosition = newTail)
            }
        }
    }

    /**
     * When the two Coordinate Systems shares the same origin the result it's a direction, otherwise it's a vector
     */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): VectorialEntity3D {
        return when (asWrittenIn.origin) {
            to.origin -> {
                val (newXComponent, newYComponent, newZComponent) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.toPoint3D().affineMatrix
                Direction3D(newXComponent, newYComponent,newZComponent)
            }
            else -> {
                val (newHeadX, newHeadY, newHeadZ) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.toPoint3D().affineMatrix
                val (newTailX, newTailY, newTailZ) = to.affineMatrix.invert() * asWrittenIn.affineMatrix * Point3D(
                    0.0,
                    0.0,
                    0.0
                ).affineMatrix
                val newHead = Point3D(newHeadX, newHeadY, newHeadZ)
                val newTail = Point3D(newTailX, newTailY, newTailZ)
                Vector3D(headPosition = newHead, tailPosition = newTail)
            }
        }
    }

}