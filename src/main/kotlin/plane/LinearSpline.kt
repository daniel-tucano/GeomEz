package plane

import plane.elements.Point2D
import space.CoordinateSystem3D
import space.elements.Point3D
import java.lang.IllegalArgumentException

class LinearSpline(override val points: List<Point2D>) : Points2DList, Function2D {

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
    private fun interpolate(x: Double): Point2D {
        if (!inXRange(x)) {
            throw IllegalArgumentException("X value out of function range")
        }
        // Finding indexes of the points where the provided x would be between
        val nextXIndex = xPoints.indexOfFirst { it > x }
        val previousXIndex = nextXIndex - 1

        /**
         * Fraction corresponding to the proximity of the provided x to the next x in
         * relation to the previous x. The closest it gets from 1 the closest it is from the
         * next point x, the closest it is from 0 the closest it is from the previous point x
         */
        val fractionOfNextX = (x - xPoints[previousXIndex]) / (xPoints[nextXIndex] - xPoints[previousXIndex])

        return points[nextXIndex] * fractionOfNextX + points[previousXIndex] * (1 - fractionOfNextX)
    }

    /**
     * Return the function with the corresponding derivatives values using the mid-point approximation
     * for inner points and lateral approximation for boundary points
     */
    override fun derivative(x: Double): Double {
        if (!inXRange(x)) {
            throw IllegalArgumentException("X value out of function range")
        }
        // Finding indexes of the points where the provided x would be between
        val nextXIndex = xPoints.indexOfFirst { it > x }
        val previousXIndex = nextXIndex - 1

        return (points[nextXIndex].y - points[previousXIndex].y) / (points[nextXIndex].x - points[previousXIndex].x)
    }


    override fun integrate(xStart: Double, xEnd: Double): Double {
        if (!inXRange(xStart) || !inXRange(xEnd)) {
            throw IllegalArgumentException("X value out of function range")
        }
        val integrationPoints =
            listOf(this.interpolate(xStart)) + this.points.filter { it.x > xStart && it.x < xEnd } + this.interpolate(
                xEnd
            )
        return integrationPoints.foldRightIndexed(0.0) { index, point2D, acc ->
            when (index) {
                integrationPoints.lastIndex -> acc
                else -> acc + (point2D.y + integrationPoints[index + 1].y) / 2 * (integrationPoints[index + 1].x - point2D.x)
            }
        }
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): List<Point2D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): List<Point3D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    override operator fun invoke(x: Double): Point2D {
        return interpolate(x)
    }
}