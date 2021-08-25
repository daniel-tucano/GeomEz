package plane

import plane.elements.Point2D
import utils.linspace

open class ParametricCurve2D(
    val xParametricFunction: Function2D,
    val yParametricFunction: Function2D
) : Function2D {

    override fun derivative(t: Double): Double = yParametricFunction.derivative(t) / xParametricFunction.derivative(t)

    override fun integrate(tStart: Double, tEnd: Double): Double = integrate(tStart,tEnd,200.toUInt())

    /**
     * @param nPoints Number of points used to perform numerical integration mathod
     */
    fun integrate(tStart: Double, tEnd: Double, nPoints: UInt): Double {
        // integration among a parametric curve it's the area under the curve, which is the integral of y * dx
        val tVec = linspace(tStart, tEnd, nPoints.toInt())
        val dt = (tEnd - tStart) / nPoints.toInt()
        return tVec.foldRightIndexed(0.0) { index, t, acc ->
            when (index) {
                tVec.lastIndex -> acc
                else -> acc + (yParametricFunction(tVec[index + 1]).y * xParametricFunction.derivative(tVec[index + 1])
                        + yParametricFunction(t).y * xParametricFunction.derivative(t)) / 2 * dt
            }
        }
    }

    override fun invoke(t: Double): Point2D {
        return Point2D(xParametricFunction(t).y, yParametricFunction(t).y)
    }
}