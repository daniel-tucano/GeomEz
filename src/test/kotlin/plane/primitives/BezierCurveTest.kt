package plane.primitives

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import plane.elements.Point2D

internal class BezierCurveTest {

    @Test
    fun `bezier curve must have first and final point at equal first and last control points`() {
        val controlPoints = listOf(Point2D(1.0, 1.0), Point2D(1.5, 2.0), Point2D(3.5, 0.2))
        val bezierCurve = BezierCurve(controlPoints)

        val firstPoint = bezierCurve(0.0)
        val lastPoint = bezierCurve(1.0)

        assertEquals(1.0, firstPoint.x, 10e-11)
        assertEquals(1.0, firstPoint.y, 10e-11)
        assertEquals(3.5, lastPoint.x, 10e-11)
        assertEquals(0.2, lastPoint.y, 10e-11)
    }

    @Test
    fun `point in the middle of bezier curve must be at the right point`() {
        val controlPoints = listOf(Point2D(1.0, 1.0), Point2D(1.5, 2.0), Point2D(3.5, 0.2), Point2D(3.0, 0.5))
        val bezierCurve = BezierCurve(controlPoints)

        val expectedBezierMiddlePoint =
            (controlPoints[0] + controlPoints[1] * 3.0 + controlPoints[2] * 3.0 + controlPoints[3]) / 8.0

        val bezierMiddlePoint = bezierCurve(0.5)

        assertEquals(expectedBezierMiddlePoint.x, bezierMiddlePoint.x, 10e-9)
        assertEquals(expectedBezierMiddlePoint.y, bezierMiddlePoint.y, 10e-9)

    }

}