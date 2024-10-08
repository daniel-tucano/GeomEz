package plane.functions

import plane.elements.Direction2D

/**
 * Functions are a set of points that satisfies the condition of ascending x values
 * and only one y value for each x value
 */
interface Function2D {

    /**
     * Approximation of the direction tangent to the point provided pointing towards x direction
     */
    fun tangentDirection(x: Double): Direction2D = Direction2D(1.0,this.derivative(x))

    /**
     * Approximation of the direction tangent to the point provided, pointing outwards of the curvature (convex side)
     */
    fun normalDirection(x: Double): Direction2D = tangentDirection(x).perpendicularDirection()

    /**
     * Return the value of the derivative on the provided x
     */
    fun derivative (x: Double): Double

    /**
     * Return the value of the area below the curve in the provided range
     */
    fun integrate (xStart: Double, xEnd: Double): Double

    /**
     * Evaluate that function at given x value
     */
    operator fun invoke(x: Double): Double

    /**
     * Evaluate that function at given x values
     */
    operator fun invoke(xArray: DoubleArray): List<Double> {
        return xArray.map { x -> this.invoke(x) }
    }

    /**
     * Evaluate that function at given x values
     */
    operator fun invoke(xCollection: Collection<Double>): List<Double> {
        return xCollection.map { x -> this(x) }
    }
}