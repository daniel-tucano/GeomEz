package Plane

import Units.Angle

interface Points2DList {

    val points: List<Point2D>

    val xPoints: List<Double>
        get() = points.map { it.x }
    val yPoints: List<Double>
        get() = points.map { it.y }

    val minX: Double
        get() = this.points.minOf { it.x }
    val maxX: Double
        get() = this.points.maxOf { it.x }

    val minY: Double
        get() = this.points.minOf { it.y }
    val maxY: Double
        get() = this.points.maxOf { it.y }

    val length: Double
        get() = points.foldRightIndexed(0.0) { index, point, acc ->
            if (index != (points.size - 1)) {
                point.distanceBetween(points[index+1])
            } else 0.0
        }

    val centroid: Point2D
        get() = Point2D(xPoints.average(), yPoints.average())

    fun inXRange(value: Double): Boolean = value in minX..maxX
    fun inYRange(value: Double): Boolean = value in minY..maxY

    fun first(): Point2D = points.first()
    fun last(): Point2D = points.last()

    operator fun get(index: Int) = this.points[index]

    /**
     * Gets a point from the list but wrap around it self when a index out of bounds it's provided
     */
    operator fun invoke(index: Int): Point2D {
        if (index >= 0) {
            return this.points[index.rem(this.points.size)]
        }
        /**
         * The sum happens because the remainder of the division it's already negative
         */
        return this.points[this.points.size + index.rem(this.points.size)]
    }

}