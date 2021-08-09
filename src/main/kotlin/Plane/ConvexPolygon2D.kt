package Plane

import Units.Angle

class ConvexPolygon2D(points: List<Point2D>) : Polygon2D(points) {

    /**
     * Checks if its convex
     */
    init {
        points.forEachIndexed { index, point2D ->
            val nextEdgeVector = (this(index + 1) - point2D).toPositionVector3D
            val prevEdgeVector = (this(index - 1) - point2D).toPositionVector3D
            if (nextEdgeVector.cross(prevEdgeVector).zComponent > 0) {
                throw IllegalArgumentException("List of points don't form a convex polygon")
            }
        }
    }
}