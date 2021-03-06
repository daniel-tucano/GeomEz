package plane.functions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import plane.elements.Point2D

internal class CubicSplineTest {

    @Test
    fun `values at provided points must be equal the evaluation of the spline at that points`() {
        val points = listOf(Point2D(1.0, 1.0), Point2D(1.5, 2.0), Point2D(1.7, 1.85), Point2D(2.0, 3.2))
        val cubicSpline = CubicSpline(points)

        val splineAtXPoint1 = cubicSpline(points[0].x)
        val splineAtXPoint2 = cubicSpline(points[1].x)
        val splineAtXPoint3 = cubicSpline(points[2].x)
        val splineAtXPoint4 = cubicSpline(points[3].x)

        assertEquals(points[0].x, splineAtXPoint1.x, 10e-10)
        assertEquals(points[1].x, splineAtXPoint2.x, 10e-10)
        assertEquals(points[2].x, splineAtXPoint3.x, 10e-10)
        assertEquals(points[3].x, splineAtXPoint4.x, 10e-10)

        assertEquals(points[0].y, splineAtXPoint1.y, 10e-10)
        assertEquals(points[1].y, splineAtXPoint2.y, 10e-10)
        assertEquals(points[2].y, splineAtXPoint3.y, 10e-10)
        assertEquals(points[3].y, splineAtXPoint4.y, 10e-10)
    }

}