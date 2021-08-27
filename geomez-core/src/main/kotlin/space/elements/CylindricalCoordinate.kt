package space.elements

import units.Angle
import utils.cos
import utils.sin

/**
 * Represents a cylindrical coordinate with positive angle in anti-clockwise direction and 0 at x-axis
 */
class CylindricalCoordinate(
    val radius: Double,
    val angle: Angle,
    val z: Double
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