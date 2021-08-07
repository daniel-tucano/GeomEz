package Space

interface Points3DList {
    abstract val points: List<Point3D>

    val length: Double
        get() = points.foldIndexed(0.0) { index, acc, point3D ->
            if (index != points.size) {
                acc + point3D.distanceBetween(points[index+1])
            } else acc
        }

    val xPoints: List<Double>
        get() = points.map { it.x }
    val yPoints: List<Double>
        get() = points.map { it.y }
    val zPoints: List<Double>
        get() = points.map { it.z }

    val minX: Double
        get() = this.points.minOf { it.x }
    val maxX: Double
        get() = this.points.maxOf { it.x }

    val minY: Double
        get() = this.points.minOf { it.y }
    val maxY: Double
        get() = this.points.maxOf { it.y }

    val minZ: Double
        get() = this.points.minOf { it.z }
    val maxZ: Double
        get() = this.points.maxOf { it.z }

    val centroid: Point3D
        get() = Point3D(xPoints.average(), yPoints.average(), zPoints.average())

    operator fun get(index: Int) = this.points[index]

    fun inXRange(value: Double): Boolean = value in minX..maxX
    fun inYRange(value: Double): Boolean = value in minY..maxY
    fun inZRange(value: Double): Boolean = value in minZ..maxZ

}