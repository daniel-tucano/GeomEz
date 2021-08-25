package space

import plane.Function2D
import space.elements.Direction3D
import space.elements.Point3D
import space.elements.Vector3D

/**
 * This abstraction provides an easy way to position single variable functions in 3D space without losing some important
 * features like interpolation, normal and tangent direction functions
 */
class Function2DInPlane(val function2D: Function2D, val plane: Plane) {

    fun interpolate(x: Double): Point3D = function2D(x).changeBasis(
        asWrittenIn = plane.coordinateSystem3D,
        to = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM
    )

    fun tangentDirection(x: Double): Direction3D {
        val tangentVectorEntity = function2D.tangentDirection(x)
            .changeBasis(asWrittenIn = plane.coordinateSystem3D, to = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM)
        return when (tangentVectorEntity) {
            is Direction3D -> tangentVectorEntity
            is Vector3D -> tangentVectorEntity.direction
        }
    }

    fun normalDirection(x: Double): Direction3D {
        val normalDirectionVector = function2D.normalDirection(x)
            .changeBasis(asWrittenIn = plane.coordinateSystem3D, to = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM)
        return when (normalDirectionVector) {
            is Direction3D -> normalDirectionVector
            is Vector3D -> normalDirectionVector.direction
        }
    }

    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Function2DInPlane {
        val newPlane = plane.changeBasis(asWrittenIn, to)
        return Function2DInPlane(function2D, newPlane)
    }

    operator fun invoke(x: Double): Point3D = interpolate(x)

}