package space

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import plane.elements.Point2D
import plane.functions.LinearSpline
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
        val function = LinearSpline(
            listOf(
                Point2D(-1.5, -1.0),
                Point2D(-1.0, 1.0),
                Point2D(0.0, 1.5),
                Point2D(1.0, 0.0),
                Point2D(2.0, -1.0)
            )
        )
        val functionInPlane = Function2DInPlane(function2D = function, plane = plane)

        val interpolatedPoint1 = functionInPlane(-1.25)
        val interpolatedPoint2 = functionInPlane(-0.5)
        val interpolatedPoint3 = functionInPlane(0.0)
        val interpolatedPoint4 = functionInPlane(0.5)

        assertTrue(interpolatedPoint1 == Point3D(1.0, 1.25, 2.0))
        assertTrue(interpolatedPoint2 == Point3D(1.0, 0.5, 3.25))
        assertTrue(interpolatedPoint3 == Point3D(1.0, 0.0, 3.5))
        assertTrue(interpolatedPoint4 == Point3D(1.0, -0.5, 2.75))
    }

    @Test
    fun `must give the right tangent direction in a function in plane`() {
        val plane = Plane(
            planeOrigin = Point3D(1.0, 0.0, 2.0),
            planeXDirection = -MAIN_Y_DIRECTION,
            planeYDirection = MAIN_Z_DIRECTION
        )
        val function = LinearSpline(
            listOf(
                Point2D(-1.5, -1.0),
                Point2D(-1.0, 1.0),
                Point2D(0.0, 1.5),
                Point2D(1.0, 0.0),
                Point2D(2.0, -1.0)
            )
        )
        val functionInPlane = Function2DInPlane(function2D = function, plane = plane)

        val tangentDirection1 = functionInPlane.tangentDirection(-1.25)
        val tangentDirection2 = functionInPlane.tangentDirection(-0.5)
        val tangentDirection3 = functionInPlane.tangentDirection(0.0)
        val tangentDirection4 = functionInPlane.tangentDirection(0.5)

        assertTrue(tangentDirection1 == Direction3D(0.0, -1.0, 4.0))
        assertTrue(tangentDirection2 == Direction3D(0.0, -1.0, 0.5))
        assertTrue(tangentDirection3 == Direction3D(0.0, -1.0, -1.5))
        assertTrue(tangentDirection4 == Direction3D(0.0, -1.0, -1.5))
    }

}