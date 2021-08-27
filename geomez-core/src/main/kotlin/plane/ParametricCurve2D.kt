package plane

import plane.elements.Point2D
import plane.functions.Function2D
import utils.linspace

open class ParametricCurve2D(
    val xParametricFunction: Function2D,
    val yParametricFunction: Function2D
) : Function2D {

    override fun derivative(x: Double): Double = yParametricFunction.derivative(x) / xParametricFunction.derivative(x)

    override fun integrate(xStart: Double, xEnd: Double): Double = integrate(xStart,xEnd,200.toUInt())

    /**
     * @param nPoints Number of points used to perform numerical integration mathod
     */
    fun integrate(xStart: Double, xEnd: Double, nPoints: UInt): Double {
        // integration among a parametric curve it's the area under the curve, which is the integral of y * dx
        val tVec = linspace(xStart, xEnd, nPoints.toInt())
        val dt = (xEnd - xStart) / nPoints.toInt()
        return tVec.foldRightIndexed(0.0) { index, t, acc ->
            when (index) {
                tVec.lastIndex -> acc
                else -> acc + (yParametricFunction(tVec[index + 1]).y * xParametricFunction.derivative(tVec[index + 1])
                        + yParametricFunction(t).y * xParametricFunction.derivative(t)) / 2 * dt
            }
        }
    }

    override fun invoke(x: Double): Point2D {
        return Point2D(xParametricFunction(x).y, yParametricFunction(x).y)
    }
}