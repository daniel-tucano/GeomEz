package plane.primitives

import plane.Function2D
import plane.elements.Point2D
import utils.factorial
import utils.linspace
import kotlin.math.pow

/**
 * Class that represent a bezier function
 */
class BezierFunction(
    private val controlPoints: List<Point2D>,
    /**
     * Number of parametrization points equally spaced between 0 and 1
     */
    nPoints: Int = 100
) : Function2D(generateBezierPoints(controlPoints, nPoints)) {


    companion object {
        private fun generateBezierPoints(controlPoints: List<Point2D>, nPoints: Int): List<Point2D> {
            /**
             *  Parametrization points equally spaced between 0 and 1
             */
            val parametrizationPoints: List<Double> = linspace(start = 0.0, stop = 1.0, num = nPoints)
            val binomial: List<Long> = (0..controlPoints.lastIndex).map {
                factorial(controlPoints.lastIndex) / (factorial(it) * factorial(controlPoints.lastIndex - it))
            }
            return parametrizationPoints.map { t ->
                var x_t = 0.0
                var y_t = 0.0
                (0..controlPoints.lastIndex).forEach { i ->
                    x_t += binomial[i] * t.pow(i) * (1 - t).pow(controlPoints.lastIndex - i) * controlPoints[i].x
                    y_t += binomial[i] * t.pow(i) * (1 - t).pow(controlPoints.lastIndex - i) * controlPoints[i].y
                }
                Point2D(x_t, y_t)
            }
        }
    }

}