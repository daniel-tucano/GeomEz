package Space

import Units.Angle

class Curve3D(override val points: List<Point3D>): Points3DList {

    constructor(vararg points3D: Point3D): this(points3D.toList())

    fun concat(curve: Curve3D): Curve3D = Curve3D(points + curve.points)

    /**
     * Rotate points in the anti-clockwise direction along the given axis
     */
    fun rotate(axis: Vector3D, angle: Angle): Curve3D {
        return Curve3D(points.map { it.rotate(axis, angle) })
    }

    // Point operations

    /**
     * Summing points is equivalent to translate the curve
     */
    operator fun plus(point: Point3D): Curve3D = Curve3D(this.points.map { it + point })
    operator fun minus(point: Point3D): Curve3D = Curve3D(this.points.map { it - point })

    // Scalar operations

    /**
     * Multiplying the points by a scalar is equivalent to scale the curve along with it's centroid
     */
    operator fun times(scalar: Double): Curve3D = Curve3D(this.points.map { it * scalar })
    operator fun div(scalar: Double): Curve3D = Curve3D(this.points.map { it / scalar })
}