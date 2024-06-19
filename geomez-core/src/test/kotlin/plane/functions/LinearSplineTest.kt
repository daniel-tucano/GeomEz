package plane.functions

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import plane.elements.Direction2D
import plane.elements.Point2D

internal class LinearSplineTest {

    @Test
    fun `must interpolate correctly in the provided points of the line`() {
        val function = LinearSpline(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.0),
                Point2D(2.0, 1.5)
            )
        )

        val interpolatedPoint1 = Point2D(0.0, function(0.0))
        val interpolatedPoint2 = Point2D(0.125, function(0.125))
        val interpolatedPoint3 = Point2D(0.75, function(0.75))
        val interpolatedPoint4 = Point2D(1.5, function(1.5))

        assertEquals(interpolatedPoint1.x, 0.0)
        assertEquals(interpolatedPoint1.y, 0.0)

        assertEquals(interpolatedPoint2.x, 0.125)
        assertEquals(interpolatedPoint2.y, 0.125)

        assertEquals(interpolatedPoint3.x, 0.75)
        assertEquals(interpolatedPoint3.y, 0.75)

        assertEquals(interpolatedPoint4.x, 1.5)
        assertEquals(interpolatedPoint4.y, 1.25)
    }

    @Test
    fun `must give the correct derivative value approximation at the inner points`() {
        val function = LinearSpline(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.25),
                Point2D(1.5, 1.0)
            )
        )

        val derivative1 = function.derivative(0.1)
        val derivative2 = function.derivative(0.3)
        val derivative3 = function.derivative(1.0)

        assertEquals(derivative1, 1.0)
        assertEquals(derivative2, 1.0)
        assertEquals(derivative3, -0.5)
    }

    @Test
    fun `must give the correct integral approximation in the given interval`() {
        val function = LinearSpline(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.25),
                Point2D(1.5, 1.0)
            )
        )

        val integral = function.integrate(0.25, 1.25)

        assertEquals(integral, 0.828125)
    }

    @Test
    fun `must throw error when try to interpolate between points that are not in the function range`() {
        val function = LinearSpline(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.0),
                Point2D(2.0, 1.5)
            )
        )

        assertThrows(IllegalArgumentException::class.java) {
            function(3.0)
        }
    }

    @Test
    fun `must throw error when tries to create function with not ascending x values`() {
        assertThrows(IllegalArgumentException::class.java) {
            LinearSpline(
                listOf(
                    Point2D(0.0, 0.0),
                    Point2D(0.5, 0.5),
                    Point2D(0.25, 1.0),
                    Point2D(2.0, 1.5)
                )
            )
        }
    }

    @Test
    fun `must throw error when tries to create function with not repeated x values`() {
        assertThrows(IllegalArgumentException::class.java) {
            LinearSpline(
                listOf(
                    Point2D(0.0, 0.0),
                    Point2D(0.5, 0.5),
                    Point2D(0.5, 1.0),
                    Point2D(2.0, 1.5)
                )
            )
        }
    }

    @Test
    fun `must give the correct tangent direction at given points`() {
        val function = LinearSpline(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.25),
                Point2D(1.5, 1.0)
            )
        )

        val tangent1 = function.tangentDirection(0.0)
        val tangent2 = function.tangentDirection(0.25)
        val tangent3 = function.tangentDirection(1.0)

        assertTrue(tangent1 == Direction2D(1.0, 1.0))
        assertTrue(tangent2 == Direction2D(1.0, 1.0))
        assertTrue(tangent3 == Direction2D(1.0, -0.5))
    }

    @Test
    fun `must give the correct normal direction at given points`() {
        val function = LinearSpline(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.25),
                Point2D(1.5, 1.0)
            )
        )

        val normal1 = function.normalDirection(0.0)
        val normal2 = function.normalDirection(0.25)
        val normal3 = function.normalDirection(1.0)

        assertTrue(normal1 == Direction2D(-1.0, 1.0))
        assertTrue(normal2 == Direction2D(-1.0, 1.0))
        assertTrue(normal3 == Direction2D(1.0, 1 / 0.5))
    }

}