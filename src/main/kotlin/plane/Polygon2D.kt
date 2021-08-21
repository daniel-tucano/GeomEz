package plane

import plane.elements.Point2D
import space.CoordinateSystem3D
import space.Curve3D
import units.Angle
import kotlin.math.absoluteValue

/**
 * Represent a closed polygon
 */
open class Polygon2D(override var points: List<Point2D>) : Points2DList {

    override val centroid: Point2D
        get() = Point2D(points.map { it.x }.average(), points.map { it.y }.average())


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

    /**
     * Rotate points in the anti-clockwise direction along the center of rotation
     */
    fun rotate(centerOfRotation: Point2D, angle: Angle): Polygon2D {
        return Polygon2D(points.map { it.rotate(centerOfRotation, angle) })
    }

    /**
     * Describe points as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Curve3D {
        return Curve3D(points.map { it.changeBasis(asWrittenIn, to) })
    }
}