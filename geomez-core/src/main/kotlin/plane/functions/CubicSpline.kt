package plane.functions

import extensions.times
import org.ejml.simple.SimpleMatrix
import plane.CoordinateSystem2D
import plane.Points2DList
import plane.elements.Point2D
import space.CoordinateSystem3D
import space.elements.Point3D
import kotlin.math.ceil
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
     * Taken from: https://timodenk.com/blog/cubic-spline-interpolation/
     */
    private fun calculateCubicSplinePolynomials(points: List<Point2D>): List<Polynomial> {
        val n = points.lastIndex
        val coefficientsMatrix = SimpleMatrix(4 * n, 4 * n)
        val resultColumn = SimpleMatrix(4 * n, 1)
        (0 until 4 * n).forEach { index ->
            when {
                (index in (0 until 2 * n)) -> {
                    val pointIndex = when {
                        (index == 0) -> 0
                        (index == (2 * n - 1)) -> n
                        else -> ceil(index / 2.0).toInt()
                    }
                    val firstColumnWrittenInRowIndex = index.floorDiv(2) * 4
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex] = 1.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 1] = points[pointIndex].x
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 2] = points[pointIndex].x.pow(2)
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 3] = points[pointIndex].x.pow(3)
                    resultColumn[index] = points[pointIndex].y
                }
                (index in (2 * n until (2 * n + 2 * (n - 2)))) -> {
                    val pointIndex = index - 2 * n + 1
                    val firstColumnWrittenInRowIndex = (index - 2 * n) * 4
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex] = 0.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 1] = 1.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 2] = 2.0 * points[pointIndex].x
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 3] = 3.0 * points[pointIndex].x.pow(2)

                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 4] = 0.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 5] = -1.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 6] = -2.0 * points[pointIndex].x
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 7] = -3.0 * points[pointIndex].x.pow(2)
                    resultColumn[index] = 0.0
                }
                (index in ((2 * n + 2 * (n - 2)) until (4 * n - 2))) -> {
                    val pointIndex = index - 2 * n - 2 * (n - 2) + 1
                    val firstColumnWrittenInRowIndex = (index - 2 * n - 2 * (n - 2)) * 4
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex] = 0.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 1] = 0.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 2] = 2.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 3] = 6.0 * points[pointIndex].x

                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 4] = 0.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 5] = 0.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 6] = -2.0
                    coefficientsMatrix[index, firstColumnWrittenInRowIndex + 7] = -6.0 * points[pointIndex].x
                    resultColumn[index] = 0.0
                }
                (index == (4 * n - 2)) -> {
                    coefficientsMatrix[index, 0] = 0.0
                    coefficientsMatrix[index, 1] = 0.0
                    coefficientsMatrix[index, 2] = 2.0
                    coefficientsMatrix[index, 3] = 6.0 * points[0].x
                    resultColumn[index] = 0.0
                }
                else -> {
                    coefficientsMatrix[index, n * 4 - 4] = 0.0
                    coefficientsMatrix[index, n * 4 - 3] = 0.0
                    coefficientsMatrix[index, n * 4 - 2] = 2.0
                    coefficientsMatrix[index, n * 4 - 1] = 6.0 * points[n].x
                    resultColumn[index] = 0.0
                }
            }
        }
        val coefficientsResults = coefficientsMatrix.invert() * resultColumn
        return (0 until n).map { index ->
            val firstPolynomialCoefficient = 4 * index
            val polynomialCoefficients = listOf(
                coefficientsResults[firstPolynomialCoefficient],
                coefficientsResults[firstPolynomialCoefficient + 1],
                coefficientsResults[firstPolynomialCoefficient + 2],
                coefficientsResults[firstPolynomialCoefficient + 3]
            )
            Polynomial(polynomialCoefficients)
        }
    }

    val polynomials: List<Polynomial> = calculateCubicSplinePolynomials(points)

    override fun derivative(x: Double): Double = polynomials[previousXInRangeIndex(x)].derivative(x)

    override fun integrate(xStart: Double, xEnd: Double): Double {
        val startPointPreviousXIndex = previousXInRangeIndex(xStart)
        val endPointPreviousXIndex = previousXInRangeIndex(xEnd)
        return (startPointPreviousXIndex..endPointPreviousXIndex).fold(0.0) { acc, i ->
            acc + when (i) {
                startPointPreviousXIndex -> polynomials[i].integrate(xStart, xPoints[i + 1])
                endPointPreviousXIndex -> polynomials[i].integrate(xPoints[i], xEnd)
                else -> polynomials[i].integrate(xPoints[i], xPoints[i + 1])
            }
        }
    }

    override fun invoke(x: Double): Double = polynomials[previousXInRangeIndex(x)](x)

    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): List<Point2D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): List<Point3D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }
}