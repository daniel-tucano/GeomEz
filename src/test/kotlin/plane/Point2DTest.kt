package plane

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Point2DTest {

//    Point operations test

    @Test
    internal fun `must add two points`() {
        val point: Point2D = Point2D(1.0,2.0) + Point2D(3.0, 4.0)

        assertEquals(4.0 , point.x)
        assertEquals(6.0 , point.y)
    }

    @Test
    internal fun `must subtract two points`() {
        val point: Point2D = Point2D(1.0,2.0) - Point2D(3.0, 4.0)

        assertEquals(-2.0 , point.x)
        assertEquals(-2.0 , point.y)
    }

    @Test
    internal fun `must multiply two points`() {
        val point: Point2D = Point2D(1.0,2.0) * Point2D(3.0, 4.0)

        assertEquals(3.0 , point.x)
        assertEquals(8.0 , point.y)
    }

    @Test
    internal fun `must divide two points`() {
        val point: Point2D = Point2D(1.0,2.0) / Point2D(3.0, 4.0)

        assertEquals(1.0/3.0 , point.x)
        assertEquals(2.0/4.0 , point.y)
    }

//    Scalar operations test

    @Test
    internal fun `must add point coordinates by scalar`() {
        val point: Point2D = Point2D(1.0,2.0) + 1.0

        assertEquals(2.0 , point.x)
        assertEquals(3.0 , point.y)
    }

    @Test
    internal fun `must subtract point coordinates by scalar`() {
        val point: Point2D = Point2D(1.0,2.0) - 1.0

        assertEquals(0.0 , point.x)
        assertEquals(1.0 , point.y)
    }

    @Test
    internal fun `must multiply point coordinates by scalar`() {
        val point: Point2D = Point2D(1.0,2.0) * 2.0

        assertEquals(2.0 , point.x)
        assertEquals(4.0 , point.y)
    }

    @Test
    internal fun `must divide point coordinates by scalar`() {
        val point: Point2D = Point2D(1.0,2.0) / 2.0

        assertEquals(0.5 , point.x)
        assertEquals(1.0 , point.y)
    }
}