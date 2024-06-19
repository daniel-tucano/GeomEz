package plane.functions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import plane.elements.Point2D

internal class CubicSplineTest {

    @Test
    fun `values at provided points must be equal the evaluation of the spline at that points`() {
        val points = listOf(
            Point2D(1.0, 1.0),
            Point2D(1.5, 2.0),
            Point2D(1.7, 1.85),
            Point2D(2.0, 3.2),
            Point2D(3.0, 5.0)
        )
        val cubicSpline = CubicSpline(points)

        val splineAtXPoint1 = cubicSpline(points[0].x)
        val splineAtXPoint2 = cubicSpline(points[1].x)
        val splineAtXPoint3 = cubicSpline(points[2].x)
        val splineAtXPoint4 = cubicSpline(points[3].x)
        val splineAtXPoint5 = cubicSpline(points[4].x)

        assertEquals(points[0].y, splineAtXPoint1, 10e-10)
        assertEquals(points[1].y, splineAtXPoint2, 10e-10)
        assertEquals(points[2].y, splineAtXPoint3, 10e-10)
        assertEquals(points[3].y, splineAtXPoint4, 10e-10)
        assertEquals(points[4].y, splineAtXPoint5, 10e-10)
    }

}