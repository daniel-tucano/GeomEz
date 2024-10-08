package space

import plane.functions.Function2D
import space.elements.Point3D

class ParametricCurve3D(
    val xParametricFunction: Function2D,
    val yParametricFunction: Function2D,
    val zParametricFunction: Function2D
) {

    operator fun invoke(t: Double): Point3D {
        return Point3D(
            xParametricFunction(t),
            yParametricFunction(t),
            zParametricFunction(t)
        )
    }

    operator fun invoke(tList: List<Double>): List<Point3D> {
        return tList.map { t ->
            Point3D(
                xParametricFunction(t),
                yParametricFunction(t),
                zParametricFunction(t)
            )
        }
    }

}