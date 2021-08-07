package Space

import Units.Angle

open class CoordinateSystem3D(
    val xDirection: Direction3D,
    val yDirection: Direction3D,
    val zDirection: Direction3D
) {

    constructor(xDirection: Direction3D, yDirection: Direction3D, origin: Point3D = Point3D(0.0, 0.0, 0.0)) : this(
        xDirection,
        yDirection,
        xDirection.cross(yDirection)
    )

    companion object {
        val MAIN_COORDINATE_SYSTEM =
            CoordinateSystem3D(
                Direction3D(1.0, 0.0, 0.0),
                Direction3D(0.0, 1.0, 0.0),
                Direction3D(0.0, 0.0, 1.0)
            )
    }

    fun rotate(axis: Direction3D, angle: Angle): CoordinateSystem3D {
        return CoordinateSystem3D(
            xDirection.rotate(axis, angle),
            yDirection.rotate(axis, angle),
            zDirection.rotate(axis, angle)
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CoordinateSystem3D

        if (xDirection != other.xDirection) return false
        if (yDirection != other.yDirection) return false
        if (zDirection != other.zDirection) return false

        return true
    }

    override fun hashCode(): Int {
        var result = xDirection.hashCode()
        result = 31 * result + yDirection.hashCode()
        result = 31 * result + zDirection.hashCode()
        return result
    }
}