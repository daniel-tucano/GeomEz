package plane

import plane.elements.Point2D
import space.CoordinateSystem3D
import space.elements.Point3D
import units.Angle
import kotlin.math.absoluteValue

/**
 * Represent a closed polygon
 */
open class Polygon2D(override var points: List<Point2D>) : Points2DList {

    /**
     * Area of the polygon
     * taken from: https://en.wikipedia.org/wiki/Polygon
     */
    val area: Double
        get() {
            return points.foldIndexed(0.0) { index, acc, point2D ->
                acc + point2D.x * this(index + 1).y - this(index + 1).x * point2D.y
            }.absoluteValue / 2
        }

    fun rotate(angle: Angle): Polygon2D {
        return Polygon2D(points.map { it.rotate(angle) })
    }

    fun rotate(centerOfRotation: Point2D, angle: Angle): Polygon2D {
        return Polygon2D(points.map { it.rotate(centerOfRotation, angle) })
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): List<Point2D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): List<Point3D> {
        return points.map { it.changeBasis(asWrittenIn, to) }
    }

}