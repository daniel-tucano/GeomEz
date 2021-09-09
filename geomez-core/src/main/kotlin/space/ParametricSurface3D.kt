package space

import space.elements.Point3D
import space.functions.Function3D

class ParametricSurface3D (
    val xParametricFunction: Function3D,
    val yParametricFunction: Function3D,
    val zParametricFunction: Function3D
) {


    operator fun invoke(x: Double, y: Double): Point3D {
        return Point3D(xParametricFunction(x,y), yParametricFunction(x,y), zParametricFunction(x,y))
    }
}