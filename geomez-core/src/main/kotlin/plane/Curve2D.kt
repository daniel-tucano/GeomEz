package plane

import plane.elements.Point2D
import space.CoordinateSystem3D
import space.elements.Point3D
import units.Angle

class Curve2D (override var points: List<Point2D>): Points2DList {

    constructor(vararg points2D: Point2D) : this(points2D.toList())

    fun concat (curve2D: Curve2D): Curve2D = Curve2D(this.points + curve2D.points)

    fun rotate(angle: Angle): Curve2D {
        return Curve2D(points.map { it.rotate(angle) })
    }

    fun rotate(centerOfRotation: Point2D, angle: Angle): Curve2D {
        return Curve2D(points.map { it.rotate(centerOfRotation, angle) })
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): List<Point2D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): List<Point3D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    override fun translateTo(newCentroid: Point2D): Curve2D {
        return Curve2D(this.points.map { it + newCentroid - this.centroid })
    }

    override fun scale(scalar: Double): Curve2D {
        return Curve2D(this.points.map { it * scalar })
    }

    // Point operations

    /**
     * Summing points is equivalent to translate the curve
     */
    operator fun plus(point: Point2D): Curve2D = Curve2D(this.points.map { it + point })
    operator fun minus(point: Point2D): Curve2D = Curve2D(this.points.map { it - point })

    // Scalar operations

    /**
     * Multiplying the points by a scalar is equivalent to scale the curve along with its centroid
     */
    operator fun times(scalar: Double): Curve2D = Curve2D(this.points.map { it * scalar })
    operator fun div(scalar: Double): Curve2D = Curve2D(this.points.map { it / scalar })
}