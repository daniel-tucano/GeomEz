package space

import extensions.equalsDelta
import space.elements.Direction3D
import space.elements.Point3D

class Plane(
    val planeOrigin: Point3D = Point3D(0.0, 0.0, 0.0),
    val planeXDirection: Direction3D,
    val planeYDirection: Direction3D
) {

    val normalDirection = planeXDirection cross planeYDirection

    val coordinateSystem3D = CoordinateSystem3D(
        planeXDirection,
        planeYDirection,
        normalDirection,
        planeOrigin
    )

    /**
     * Checks if the point belong to the plane
     */
    fun pointIsInPlane(point: Point3D): Boolean {
        return normalDirection.dot((point - planeOrigin).asVector3D()).equalsDelta(0.0, delta = 1e-7)
    }

    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Plane {
        return Plane(
            planeOrigin = planeOrigin.changeBasis(asWrittenIn, to),
            planeXDirection = planeXDirection.changeBasis(asWrittenIn, to).direction,
            planeYDirection = planeYDirection.changeBasis(asWrittenIn, to).direction
        )
    }

}