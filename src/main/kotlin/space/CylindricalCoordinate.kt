package space

import space.elements3D.Direction3D
import space.elements3D.Point3D
import space.elements3D.Vector3D
import units.Angle
import units.cos
import units.sin

/**
 * Represents a cylindrical coordinate with positive angle in anti-clockwise direction and 0 at x axis
 */
class CylindricalCoordinate(
    val radius: Double,
    val angle: Angle,
    val z: Double,
    val coordinateSystem3D: CoordinateSystem3D = CoordinateSystem3D.MAIN_COORDINATE_SYSTEM
) {

    fun toPoint3D(): Point3D = Point3D(radius * cos(angle),radius * sin(angle),z)
    fun toVector3D(): Vector3D = Vector3D(radius * cos(angle),radius * sin(angle),z)

    /**
     * Returns the tangent towards the anti-clockwise direction
     */
    val tangentDirection: Direction3D
        get() = Direction3D.MAIN_Z_DIRECTION cross this.toVector3D().direction

    /**
     * Returns the normal direction
     */
    val normalDirection: Direction3D
        get() = this.toVector3D().direction
}