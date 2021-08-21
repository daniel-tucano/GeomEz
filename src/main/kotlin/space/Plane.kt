package space

import space.CoordinateSystem3D.Companion.MAIN_COORDINATE_SYSTEM
import space.elements3D.Direction3D
import space.elements3D.Point3D

/**
 * Z direction from localCoordinateSystem must be the direction normal to the plane
 */
class Plane(val origin: Point3D = Point3D(0.0,0.0,0.0), localCoordinateSystem: CoordinateSystem3D = MAIN_COORDINATE_SYSTEM) {

    val normalVector: Direction3D = localCoordinateSystem.zDirection
}