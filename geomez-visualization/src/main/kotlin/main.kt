import plane.functions.Polynomial
import plane.plot
import space.Function2DInPlane
import space.Plane
import space.elements.Direction3D
import utils.linspace

fun main() {

    val xValues = linspace(0.0,10.0,100)

    val function = Polynomial(listOf(0.0,0.0,1.0))
    val plane = Plane(planeXDirection = Direction3D.MAIN_X_DIRECTION, planeYDirection = Direction3D.MAIN_Z_DIRECTION)
    val functionInPlane = Function2DInPlane(function,plane)
    val pointsList = functionInPlane(xValues)

    pointsList.plot()

}