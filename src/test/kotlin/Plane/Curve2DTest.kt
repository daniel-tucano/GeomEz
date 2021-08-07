package Plane

import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals

internal class Curve2DTest {

    @Test
    internal fun `must get all x values`() {
        val curve = Curve2D(Point2D(1.0, 2.0), Point2D(3.0, 4.0), Point2D(5.0, 6.0))
        val xPoints = curve.xPoints

        assertEquals(listOf(1.0,3.0,5.0), xPoints)
    }

    @Test
    internal fun `must get all y values`() {
        val curve = Curve2D(Point2D(1.0, 2.0), Point2D(3.0, 4.0), Point2D(5.0, 6.0))
        val yPoints = curve.yPoints

        assertEquals(listOf(2.0,4.0,6.0), yPoints)
    }
}