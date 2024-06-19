package plane

import plane.elements.Point2D
import plane.functions.Function2D
import plane.functions.LinearSpline
import space.CoordinateSystem3D
import space.elements.Point3D

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
                acc + point.distanceBetween(points[index+1])
            } else acc
        }

    val centroid: Point2D
        get() = Point2D(xPoints.average(), yPoints.average())

    fun inXRange(value: Double): Boolean = value in minX..maxX
    fun inYRange(value: Double): Boolean = value in minY..maxY

    fun first(): Point2D = points.first()
    fun last(): Point2D = points.last()

    fun xParametricFunction(): Function2D {
        return LinearSpline(this.xPoints.mapIndexed { index, x -> Point2D(index.toDouble()/(xPoints.size - 1), x) })
    }

    fun yParametricFunction(): Function2D {
        return LinearSpline(this.yPoints.mapIndexed { index, y -> Point2D(index.toDouble()/(yPoints.size - 1), y) })
    }

    /**
     * Describe point as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): List<Point2D>

    /**
     * Describe point as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): List<Point3D>

    fun translateTo(newCentroid: Point2D): Points2DList

    fun scale(scalar: Double): Points2DList

    operator fun get(index: Int) = this.points[index]

    /**
     * Gets a point from the list but wrap around itself when an index out of bounds it's provided
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