package plane

import org.ejml.simple.SimpleMatrix
import plane.elements.Direction2D
import plane.elements.Point2D
import units.Angle

class CoordinateSystem2D(
    val xDirection: Direction2D = Direction2D.MAIN_X_DIRECTION,
    val yDirection: Direction2D = Direction2D.MAIN_Y_DIRECTION,
    val origin: Point2D = Point2D(0.0,0.0)
) {

    companion object {
        val MAIN_2D_COORDINATE_SYSTEM =
            CoordinateSystem2D(
                Direction2D.MAIN_X_DIRECTION,
                Direction2D.MAIN_Y_DIRECTION,
                Point2D(0.0, 0.0)
            )
    }

    /**
     * Matrix used for affine transformations that perform scaling and translation
     */
    val affineMatrix: SimpleMatrix
        get() {
            val (x1, x2) = xDirection
            val (y1, y2) = yDirection
            val (p1, p2) = origin

            return SimpleMatrix(
                arrayOf(
                    doubleArrayOf(x1, y1, p1),
                    doubleArrayOf(x2, y2, p2),
                    doubleArrayOf(0.0, 0.0, 1.0)
                )
            )
        }

    fun rotate( angle: Angle): CoordinateSystem2D {
        return CoordinateSystem2D(
            xDirection.rotate(angle),
            yDirection.rotate(angle)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CoordinateSystem2D

        if (xDirection != other.xDirection) return false
        if (yDirection != other.yDirection) return false
        if (origin != other.origin) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xDirection.hashCode()
        result = 31 * result + yDirection.hashCode()
        result = 31 * result + origin.hashCode()
        return result
    }

}