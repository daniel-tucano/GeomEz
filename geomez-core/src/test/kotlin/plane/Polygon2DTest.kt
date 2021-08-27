package plane

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import plane.elements.Point2D

internal class Polygon2DTest {
    @Test
    fun `must calculate the area of a triangle correctly`() {
        val polygon = Polygon2D(listOf(Point2D(3.0,2.0), Point2D(1.0,1.0), Point2D(4.0,0.0)))
        val area: Double = polygon.area

        assertEquals(2.5,area, 10e-6)
    }
}