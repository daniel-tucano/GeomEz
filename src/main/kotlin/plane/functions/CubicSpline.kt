package plane.functions

import extensions.times
import extensions.toList
import org.ejml.simple.SimpleMatrix
import plane.CoordinateSystem2D
import plane.Points2DList
import plane.elements.Point2D
import space.CoordinateSystem3D
import space.elements.Point3D
import kotlin.math.pow

/**
 * Represents a "natural" cubic spline, which have second order derivatives at the end points equal to 0
 */
class CubicSpline(
    override val points: List<Point2D>
) : Function2D, Points2DList {

    /**
     * Certifies that all x values are in ascending order and do not have repetition
     */
    init {
        if (xPoints.size < 4) {
            throw IllegalArgumentException("Cubic spline must have at least 4 points")
        } else if (xPoints.sorted() != xPoints) {
            throw IllegalArgumentException("X values are not in ascending order")
        } else if (xPoints != xPoints.distinct()) {
            throw IllegalArgumentException("Repeated X value present")
        }
    }

    /**
     * @see https://mathworld.wolfram.com/CubicSpline.html
     */
    private fun calculateCList(points: List<Point2D>): List<Double> {
        val n = points.lastIndex
        val theta = MutableList(n + 2) { 0.0 }
        (0..n + 1).forEach { i ->
            theta[i] = when (i) {
                0 -> 1.0
                1 -> 2.0
                n + 1 -> 2 * theta[i - 1] - theta[i - 2]
                else -> 4 * theta[i - 1] - theta[i - 2]
            }
        }
        val psi = theta.reversed()
        val inverseMatrixArray = Array(n + 1) { DoubleArray(n + 1) { 0.0 } }
        for (i in 0..n) {
            for (j in 0..n) {
                inverseMatrixArray[i][j] = when {
                    i == j -> {
                        theta[i] * psi[j + 1] / theta[n + 1]
                    }
                    i < j -> {
                        (-1.0).pow(i + j) * theta[i] * psi[j + 1] / theta[n + 1]
                    }
                    else -> {
                        (-1.0).pow(i + j) * theta[j] * psi[i + 1] / theta[n + 1]
                    }
                }
            }
        }
        val inverseMatrix = SimpleMatrix(inverseMatrixArray)
        val y = SimpleMatrix(
            Array(n + 1) { DoubleArray(1) { 0.0 } }.onEachIndexed { line, doubles ->
                doubles[0] = when (line) {
                    0 -> 3 * (points[1].y - points[0].y)
                    n -> 3 * (points[n].y - points[n - 1].y)
                    else -> 3 * (points[line + 1].y - points[line - 1].y)
                }
            }
        )
        return (inverseMatrix * y).toList()
    }

    private fun previousXInRangeIndex(x: Double): Int {
        if (!inXRange(x)) {
            throw java.lang.IllegalArgumentException("X value out of function range")
        }
        // Finding indexes of the points where the provided x would be between
        var previousXIndex = xPoints.indexOfFirst { it > x } - 1
        if (previousXIndex < 0) {
            previousXIndex = xPoints.lastIndex - 1
        }
        return previousXIndex
    }

    /**
     * @see Burden, R. L.; Faires, J. D.; and Reynolds, A. C. Numerical Analysis, 9th ed. Boston, MA: Brooks/Cole pp 149-150
     */
    private fun calculateCubicSplinePolynomials(points: List<Point2D>): List<Polynomial> {
        val n = points.lastIndex
        val h = (0..n - 1).map { i -> points[i + 1].x - points[i].x }
        val a = points.map { it.y }
        val c = calculateCList(points)
        val b = (0..n - 1).map { i ->
            (a[i + 1] - a[i]) / h[i] - h[i] * (c[i + 1] + 2 * c[i]) / 3
        }
        val d = (0..n - 1).map { i -> (c[i + 1] - c[i]) / (3 * h[i]) }
        return (0..n - 1).map { i ->
            Polynomial(listOf(-points[i].x, 1.0)) * b[i] + Polynomial(
                listOf(
                    -points[i].x,
                    1.0
                )
            ).pow(2) * c[i] + Polynomial(
                listOf(-points[i].x, 1.0)
            ).pow(3) * d[i] + a[i]
        }
    }

    val polynomials: List<Polynomial> = calculateCubicSplinePolynomials(points)

    override fun derivative(x: Double): Double = polynomials[previousXInRangeIndex(x)].derivative(x)

    override fun integrate(xStart: Double, xEnd: Double): Double {
        val startPointPreviousXIndex = previousXInRangeIndex(xStart)
        val endPointPreviousXIndex = previousXInRangeIndex(xEnd)
        return (startPointPreviousXIndex..endPointPreviousXIndex).fold(0.0) { acc, i ->
            when (i) {
                startPointPreviousXIndex -> polynomials[i].integrate(xStart, xPoints[i + 1])
                endPointPreviousXIndex -> polynomials[i].integrate(xPoints[i], xEnd)
                else -> polynomials[i].integrate(xPoints[i], xPoints[i+1])
            }
        }
    }

    override fun invoke(x: Double): Point2D = polynomials[previousXInRangeIndex(x)](x)

    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): List<Point2D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): List<Point3D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }
}