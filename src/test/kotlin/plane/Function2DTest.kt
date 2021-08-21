package plane

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import plane.elements.Direction2D
import plane.elements.Point2D

internal class Function2DTest {

    @Test
    fun `must interpolate correctly in the provided points of the line`() {
        val function = Function2D(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.0),
                Point2D(2.0, 1.5)
            )
        )

        val interpolatedPoint1 = function.interpolate(0.0)
        val interpolatedPoint2 = function.interpolate(0.125)
        val interpolatedPoint3 = function.interpolate(0.75)
        val interpolatedPoint4 = function.interpolate(1.5)

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
    fun `must give the correct derivative value approximation at the borders`() {
        val function = Function2D(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.25),
                Point2D(1.5, 1.0)
            )
        )

        val derivativeFunction = function.derivative
        val firstPointDerivative = derivativeFunction.first()
        val lastPointDerivative = derivativeFunction.last()

        assertEquals(firstPointDerivative.x, 0.0)
        assertEquals(firstPointDerivative.y, 1.0)

        assertEquals(lastPointDerivative.x, 1.5)
        assertEquals(lastPointDerivative.y, -0.5)
    }

    @Test
    fun `must give the correct derivative value approximation at the inner points`() {
        val function = Function2D(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.25),
                Point2D(1.5, 1.0)
            )
        )

        val derivativeFunction = function.derivative
        val secondPointDerivative = derivativeFunction[1]
        val thirdPointDerivative = derivativeFunction[2]

        assertEquals(secondPointDerivative.x, 0.5)
        assertEquals(secondPointDerivative.y, 1.25)

        assertEquals(thirdPointDerivative.x, 1.0)
        assertEquals(thirdPointDerivative.y, 0.5)
    }

    @Test
    fun `must give the correct integral approximation in the given interval`() {
        val function = Function2D(
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
        val function = Function2D(
            listOf(
                Point2D(0.0, 0.0),
                Point2D(0.5, 0.5),
                Point2D(1.0, 1.0),
                Point2D(2.0, 1.5)
            )
        )

        assertThrows(IllegalArgumentException::class.java) {
            function.interpolate(3.0)
        }
    }

    @Test
    fun `must throw error when tries to create function with not ascending x values`() {
        assertThrows(IllegalArgumentException::class.java) {
            Function2D(
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
            Function2D(
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
        val function = Function2D(
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
        assertTrue(tangent2 == Direction2D(1.0, 1.125))
        assertTrue(tangent3 == Direction2D(1.0, 0.5))
    }

    @Test
    fun `must give the correct normal direction at given points`() {
        val function = Function2D(
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
        assertTrue(normal2 == Direction2D(-1.0, 1 / 1.125))
        assertTrue(normal3 == Direction2D(-1.0, 1 / 0.5))
    }

}