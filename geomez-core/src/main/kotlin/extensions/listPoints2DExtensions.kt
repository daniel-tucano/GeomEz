package plane.elements

val List<Point2D>.xValues: List<Double>
    get() = this.map { it.x }
val List<Point2D>.yValues: List<Double>
    get() = this.map { it.y }
