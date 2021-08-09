package Plane

import Utils.convexHull
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

internal class ConvexPolygon2DTest {

    @Test
    fun `must throw exception when try to create a convex polygon with points that do not satisfy convex criteria`() {
        assertThrows(IllegalArgumentException::class.java) {
            ConvexPolygon2D(
                listOf(
                    Point2D(0.0, 0.0),
                    Point2D(1.0, 1.0),
                    Point2D(1.5, 0.5),
                    Point2D(2.0, 1.0),
                    Point2D(2.5, 0.0)
                )
            )
        }
    }

    @Test
    fun `must not throw exception when try to create a convex polygon with points that satisfy convex criteria`() {
        assertDoesNotThrow {
            ConvexPolygon2D(
                listOf(
                    Point2D(0.0, 0.0),
                    Point2D(1.0, 1.0),
                    Point2D(2.0, 1.0),
                    Point2D(2.5, 0.0)
                )
            )
        }
    }

    @Test
    fun `must create convex polygon given the set of points`() {
        assertDoesNotThrow {
            val (convexPolygon, remainingPoints) = convexHull(
                listOf(
                    Point2D(0.0, 0.0),
                    Point2D(1.0, 1.0),
                    Point2D(1.5, 0.5),
                    Point2D(2.0, 1.0),
                    Point2D(2.5, 0.0)
                )
            )

            assertTrue(remainingPoints.contains(Point2D(1.5, 0.5)))
            assertTrue(
                convexPolygon.points.containsAll(
                    listOf(
                        Point2D(0.0, 0.0),
                        Point2D(1.0, 1.0),
                        Point2D(2.0, 1.0),
                        Point2D(2.5, 0.0)
                    )
                )
            )
        }
    }

}
