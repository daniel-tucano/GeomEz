package plane.utils

import plane.elements.Point2D

fun Pair<List<Double>, List<Double>>.toPoint2DList(): List<Point2D> {
    val x = this.first
    val y = this.second
    return x.mapIndexed { index, xValue -> Point2D(xValue, y[index]) }
}