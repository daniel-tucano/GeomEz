package plane

import plane.elements.Point2D
import plane.functions.Polynomial
import utils.factorial
import kotlin.math.pow

/**
 * Class that represent a bezier curve
 */
class BezierCurve(
    val controlPoints: List<Point2D>
) : ParametricCurve2D(createXBezierPolynomial(controlPoints), createYBezierPolynomial(controlPoints)) {

    companion object {
        private fun createXBezierPolynomial(controlPoints: List<Point2D>): Polynomial {
            val n = controlPoints.lastIndex
            val coeffiecients = (0..n).map { j ->
                factorial(n) / factorial(n - j) * (0..j).fold(0.0) { acc, i ->
                    acc + controlPoints[i].x * (-1.0).pow(i + j) / (factorial(i) * factorial(j - i))
                }
            }
            return Polynomial(coeffiecients)
        }

        private fun createYBezierPolynomial(controlPoints: List<Point2D>): Polynomial {
            val n = controlPoints.lastIndex
            val coeffiecients = (0..n).map { j ->
                factorial(n) / factorial(n - j) * (0..j).fold(0.0) { acc, i ->
                    acc + controlPoints[i].y * (-1.0).pow(i + j) / (factorial(i) * factorial(j - i))
                }
            }
            return Polynomial(coeffiecients)
        }
    }

    override operator fun invoke(x: Double): Point2D {
        if(x == 0.0) {
            return controlPoints.first()
        } else if (x == 1.0) {
            return controlPoints.last()
        }
        return Point2D(xParametricFunction(x), yParametricFunction(x))
    }

}