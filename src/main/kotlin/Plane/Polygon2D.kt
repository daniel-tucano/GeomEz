package Plane

import Space.CoordinateSystem3D
import Units.Angle
import kotlin.math.absoluteValue

/**
 * Represent a closed polygon
 */
open class Polygon2D(points: List<Point2D>, val coordinateSystem: CoordinateSystem3D = CoordinateSystem3D.MAIN_COORDINATE_SYSTEM) : Points2DList {

    final override var points = points
        private set


    val distinctPoints = points.dropLast(1)

    override val centroid: Point2D
        get() = Point2D(distinctPoints.map { it.x }.average(), distinctPoints.map { it.y }.average())

    /**
     * make sure that the first and last points are the same
     */
    init {
        if (points.first() != points.last()) {
            this.points = points + points.first()
        }
    }

    /**
     * Area of the polygon
     * taken from: https://en.wikipedia.org/wiki/Polygon
     */
    val area: Double
        get() {
            return points.foldIndexed(0.0) { index, acc, point2D ->
                if (index != (points.size - 1)) {
                    acc + point2D.x * points[index + 1].y - points[index + 1].x * point2D.y
                } else acc
            }.absoluteValue / 2
        }

    /**
     * Rotate points in the anti-clockwise direction along the center of rotation
     */
    fun rotate(centerOfRotation: Point2D, angle: Angle): Polygon2D {
        return Polygon2D(points.map { it.rotate(centerOfRotation, angle) })
    }
}