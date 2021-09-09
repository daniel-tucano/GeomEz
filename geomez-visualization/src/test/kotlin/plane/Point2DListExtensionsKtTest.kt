package plane

import org.junit.jupiter.api.Test
import plane.elements.Point2D

internal class Point2DListExtensionsKtTest {

    @Test
    fun `must create points map correctly` () {
        val pointsList = listOf(
            Point2D(0.0, 1.0),
            Point2D(1.0, 1.5),
            Point2D(2.0,0.5)
        )

        pointsList.plot()
    }

}