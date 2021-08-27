package plane.functions

import plane.elements.Direction2D
import plane.elements.Point2D

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
     * Return the value of the derivative on hte provided x
     */
    fun derivative (x: Double): Double

    /**
     * Return the value of the area below the curve in the provided range
     */
    fun integrate (xStart: Double, xEnd: Double): Double

    operator fun invoke(x: Double): Point2D
}