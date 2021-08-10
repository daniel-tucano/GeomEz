package Plane.Primitives

import Plane.Point2D
import Plane.PolarCoordinate
import Plane.Polygon2D
import Space.CoordinateSystem3D
import Units.Angle

/**
 * Represents a circle in with it's center at the origin of the coordinate system
 */
class Circle(nPoints: Int, radius: Double) : Polygon2D(constructCirclePoints(nPoints, radius)) {

    companion object {
        private fun constructCirclePoints(nPoints: Int, radius: Double): List<Point2D> {
            val pointsCircle: MutableList<Point2D> = mutableListOf()
            for (i in 0..nPoints) {
                pointsCircle.add(PolarCoordinate(radius, Angle.Degrees(i * 360.0/nPoints)).toPoint2D())
            }
            return pointsCircle.toList()
        }
    }

}