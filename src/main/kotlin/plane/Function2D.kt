package plane

import space.CoordinateSystem3D
import space.Curve3D
import space.elements3D.Direction3D
import java.lang.IllegalArgumentException

/**
 * Functions are a set of points that satisfies the condition of ascending x values
 * and only one y value for each x value
 */
open class Function2D(override val points: List<Point2D>) : Points2DList {

    /**
     * Certifies that all x values are in ascending order and do not have repetition
     */
    init {
        if (xPoints.sorted() != xPoints) {
            throw IllegalArgumentException("X values are not in ascending order")
        } else if (xPoints != xPoints.distinct()) {
            throw IllegalArgumentException("Repeated X value present")
        }
    }

    /**
     * Get Y value interpolating linearly along provided points
     */
    fun interpolate(x: Double): Point2D {
        if (!inXRange(x)) {
            throw IllegalArgumentException("X value out of function range")
        }
        // Finding indexes of the points where the provided x would be between
        val nextI = xPoints.indexOfFirst { it > x }
        val prevI = nextI - 1

        /**
         * Fraction corresponding to the proximity of the provided x to the next x in
         * relation to the previous x. The closest it gets from 1 the closest it is from the
         * next point x, the closest it is from 0 the closest it is from the previous point x
         */
        val fracNext = (x - xPoints[prevI]) / (xPoints[nextI] - xPoints[prevI])

        return points[nextI] * fracNext + points[prevI] * (1 - fracNext)
    }

    /**
     * Approximation of the direction tangent to the point provided pointing towards x direction
     */
    fun tangentDirection(x: Double): Direction3D {
        return Direction3D(1.0,this.derivative.interpolate(x).y,0.0)
    }

    /**
     * Approximation of the direction tangent to the point provided, pointing outwards of the curvature (convex side)
     */
    fun normalDirection(x: Double): Direction3D {
        return Direction3D.MAIN_Z_DIRECTION cross tangentDirection(x)
    }

    /**
     * Return the function with the corresponding derivatives values using the mid point approximation
     * for inner points and lateral approximation for boundary points
     */
    val derivative: Function2D
        get() {
        val derivativeFirstPoint =
            Point2D(xPoints.first(), (yPoints.first() - yPoints[1]) / (xPoints.first() - xPoints[1]))
        val derivativeLastPoint = Point2D(
            xPoints.last(),
            (yPoints.last() - yPoints[yPoints.lastIndex - 1]) / (xPoints.last() - xPoints[xPoints.lastIndex - 1])
        )
        return Function2D(points.mapIndexed { index, point2D ->
            when (index) {
                0 -> derivativeFirstPoint
                points.lastIndex -> derivativeLastPoint
                else -> Point2D(
                    point2D.x,
                    (points[index + 1].y - points[index - 1].y) / (points[index + 1].x - points[index - 1].x)
                )
            }
        }
        )
    }

    /**
     * Return the value of the area below the curve in the provided range
     */
    fun integrate (xStart: Double, xEnd: Double): Double {
        if (!inXRange(xStart) || !inXRange(xEnd)) {
            throw IllegalArgumentException("X value out of function range")
        }
        val integrationPoints = listOf(this.interpolate(xStart)) + this.points.filter { it.x > xStart && it.x < xEnd } + this.interpolate(xEnd)
        return integrationPoints.foldRightIndexed (0.0) {index, point2D, acc ->
            when(index) {
                integrationPoints.lastIndex -> acc
                else -> acc + (point2D.y + integrationPoints[index+1].y)/2 * (integrationPoints[index+1].x - point2D.x)
            }
        }
    }

    /**
     * Describe points as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Curve3D {
        return Curve3D(points.map { it.changeBasis(asWrittenIn, to) })
    }
}