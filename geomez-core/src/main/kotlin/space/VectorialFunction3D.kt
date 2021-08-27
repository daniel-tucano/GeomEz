package space

import plane.functions.Function2D
import space.elements.Vector3D

class VectorialFunction3D(
    val xComponentFunction: Function2D,
    val yComponentFunction: Function2D,
    val zComponentFunction: Function2D
) {

    operator fun invoke(t: Double): Vector3D {
        return Vector3D(
            xComponentFunction(t).y,
            yComponentFunction(t).y,
            zComponentFunction(t).y
        )
    }

}