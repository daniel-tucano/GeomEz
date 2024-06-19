package plane

import plane.elements.Point2D

class ConvexPolygon2D(points: List<Point2D>) : Polygon2D(points) {

    /**
     * Checks if its convex
     */
    init {
        points.forEachIndexed { index, point2D ->
            val nextEdgeVector = (this(index + 1) - point2D).toVector3D
            val prevEdgeVector = (this(index - 1) - point2D).toVector3D
            if (nextEdgeVector.cross(prevEdgeVector).z > 0) {
                throw IllegalArgumentException("List of points don't form a convex polygon")
            }
        }
    }

    override fun scale(scalar: Double): ConvexPolygon2D {
        return ConvexPolygon2D(points.map { point2D -> point2D * scalar })
    }
}