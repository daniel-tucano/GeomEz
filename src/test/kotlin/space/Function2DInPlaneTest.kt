package space

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import plane.Function2D
import plane.elements.Point2D
import space.elements.Direction3D
import space.elements.Direction3D.Companion.MAIN_Y_DIRECTION
import space.elements.Direction3D.Companion.MAIN_Z_DIRECTION
import space.elements.Point3D

internal class Function2DInPlaneTest {

    @Test
    fun `must give the right point coordinates when interpolating a function in plane`() {
        val plane = Plane(
            planeOrigin = Point3D(1.0, 0.0, 2.0),
            planeXDirection = -MAIN_Y_DIRECTION,
            planeYDirection = MAIN_Z_DIRECTION
        )
        val function = Function2D(listOf(
            Point2D(-1.5,-1.0),
            Point2D(-1.0, 1.0),
            Point2D( 0.0, 1.5),
            Point2D( 1.0, 0.0),
            Point2D( 2.0,-1.0)
        ))
        val functionInPlane = Function2DInPlane(function2D = function, plane = plane)

        val interpolatedPoint1 = functionInPlane(-1.25)
        val interpolatedPoint2 = functionInPlane(-0.5)
        val interpolatedPoint3 = functionInPlane(0.0)
        val interpolatedPoint4 = functionInPlane(0.5)

        assertTrue(interpolatedPoint1.equals(Point3D(1.0, 1.25, 2.0)))
        assertTrue(interpolatedPoint2.equals(Point3D(1.0, 0.5, 3.25)))
        assertTrue(interpolatedPoint3.equals(Point3D(1.0, 0.0, 3.5)))
        assertTrue(interpolatedPoint4.equals(Point3D(1.0, -0.5, 2.75)))
    }

    @Test
    fun `must give the right tangent direction in a function in plane`() {
        val plane = Plane(
            planeOrigin = Point3D(1.0, 0.0, 2.0),
            planeXDirection = -MAIN_Y_DIRECTION,
            planeYDirection = MAIN_Z_DIRECTION
        )
        val function = Function2D(listOf(
            Point2D(-1.5,-1.0),
            Point2D(-1.0, 1.0),
            Point2D( 0.0, 1.5),
            Point2D( 1.0, 0.0),
            Point2D( 2.0,-1.0)
        ))
        val functionInPlane = Function2DInPlane(function2D = function, plane = plane)

        val tangentDirection1 = functionInPlane.tangentDirection(-1.25)
        val tangentDirection2 = functionInPlane.tangentDirection(-0.5)
        val tangentDirection3 = functionInPlane.tangentDirection(0.0)
        val tangentDirection4 = functionInPlane.tangentDirection(0.5)

        assertTrue(tangentDirection1.equals(Direction3D(0.0, -1.0, 17.0/6)))
        assertTrue(tangentDirection2.equals(Direction3D(0.0, -1.0, 3.5/6)))
        assertTrue(tangentDirection3.equals(Direction3D(0.0, -2.0, -1.0)))
        assertTrue(tangentDirection4.equals(Direction3D(0.0, -1.0, -1.5)))
    }

}