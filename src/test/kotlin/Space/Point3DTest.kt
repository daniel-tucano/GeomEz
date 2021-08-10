package Space

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Point3DTest {
    @Test
    fun `must write point in another coordinate system`() {
        val point = Point3D(1.0, 1.0, 0.0)
        val coordinateSystem = CoordinateSystem3D(
            xDirection = -Direction3D.MAIN_Y_DIRECTION,
            yDirection = Direction3D.MAIN_Z_DIRECTION,
            origin = Point3D(1.0, 0.0, 1.0)
        )
        val pointInMainCoordinateSystem = point.changeBasis(coordinateSystem, CoordinateSystem3D.MAIN_COORDINATE_SYSTEM)

        assertEquals(1.0,pointInMainCoordinateSystem.x)
        assertEquals(-1.0,pointInMainCoordinateSystem.y)
        assertEquals(2.0,pointInMainCoordinateSystem.z)
    }
}