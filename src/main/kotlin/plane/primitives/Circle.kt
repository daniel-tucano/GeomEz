package plane.primitives

import plane.elements.Point2D
import plane.elements.PolarCoordinate
import plane.Polygon2D
import units.Angle

/**
 * Represents a circle in with its center at the origin of the coordinate system
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