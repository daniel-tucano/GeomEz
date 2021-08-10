package Space

import Units.Angle
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.math.sqrt

internal class Vector3DTest {

    @Test
    internal fun `must rotate vector 30 degrees around arbitrary vector axis`() {
        val axisOfRotation = Vector3D(0.0,0.0,10.0, position = Point3D(1.0,1.0,0.0))
        val vector3D = Vector3D(-1.0, 0.0,0.0, position = Point3D(0.0,1.0,0.0))
        val rotatedVector = vector3D.rotate(axisOfRotation, Angle.Degrees(30.0))

        assertEquals(- sqrt(3.0)/2,rotatedVector.xComponent, 10.0e-6)
        assertEquals(-0.5,rotatedVector.yComponent, 10.0e-6)
        assertEquals(0.0,rotatedVector.zComponent, 10.0e-6)

        assertEquals(1 - sqrt(3.0)/2,rotatedVector.position.x, 10.0e-6)
        assertEquals(0.5,rotatedVector.position.y, 10.0e-6)
        assertEquals(0.0,rotatedVector.position.z, 10.0e-6)
    }

    @Test
    internal fun `must rotate vector 45 degrees in x axis`() {
        val vector3D = Vector3D(1.0,sqrt(2.0)/2,sqrt(2.0)/2)
        val rotatedVector = vector3D.rotate(Direction3D.MAIN_X_DIRECTION, Angle.Degrees(45.0))

        assertEquals(1.0,rotatedVector.xComponent, 10.0e-6)
        assertEquals(0.0,rotatedVector.yComponent, 10.0e-6)
        assertEquals(1.0,rotatedVector.zComponent, 10.0e-6)
    }

    @Test
    internal fun `must rotate vector 45 degrees in y axis`() {
        val vector3D = Vector3D(sqrt(2.0)/2,1.0,sqrt(2.0)/2)
        val rotatedVector = vector3D.rotate(Direction3D.MAIN_Y_DIRECTION, Angle.Degrees(45.0))

        assertEquals(1.0,rotatedVector.xComponent, 10.0e-6)
        assertEquals(1.0,rotatedVector.yComponent, 10.0e-6)
        assertEquals(0.0,rotatedVector.zComponent, 10.0e-6)
    }

    @Test
    internal fun `must rotate vector 45 degrees in z axis`() {
        val vector3D = Vector3D(sqrt(2.0)/2,sqrt(2.0)/2,1.0)
        val rotatedVector = vector3D.rotate(Direction3D.MAIN_Z_DIRECTION, Angle.Degrees(45.0))

        assertEquals(0.0,rotatedVector.xComponent, 10.0e-6)
        assertEquals(1.0,rotatedVector.yComponent, 10.0e-6)
        assertEquals(1.0,rotatedVector.zComponent, 10.0e-6)
    }

    @Test
    internal fun `must calculate the angle between two vectors`() {
        val vector3D_1_1 = Vector3D(1.0,0.0,0.0)
        val vector3D_1_2 = Vector3D(0.0,1.0,0.0)
        val angleBetween_1 = vector3D_1_1.angleBetween(vector3D_1_2)

        assertEquals(90.0, angleBetween_1.toDegrees().value, 10e-6)

        val vector3D_2_1 = Vector3D(0.5,0.5,0.0)
        val vector3D_2_2 = Vector3D(0.0,1.0,0.0)
        val angleBetween_2 = vector3D_2_1.angleBetween(vector3D_2_2)

        assertEquals(45.0, angleBetween_2.toDegrees().value, 10e-6)
    }

    @Test
    fun `must write vector in another coordinate system`() {
        val vector = Vector3D(1.0, 1.0, 0.0)
        val coordinateSystem = CoordinateSystem3D(
            xDirection = -Direction3D.MAIN_Y_DIRECTION,
            yDirection = Direction3D.MAIN_Z_DIRECTION,
            zDirection = -Direction3D.MAIN_X_DIRECTION,
            origin = Point3D(1.0, 0.0, 1.0)
        )
        val vectorInMainCoordinateSystem = vector.changeBasis(coordinateSystem, CoordinateSystem3D.MAIN_COORDINATE_SYSTEM)

        assertEquals(1.0,vectorInMainCoordinateSystem.xComponent)
        assertEquals(-1.0,vectorInMainCoordinateSystem.yComponent)
        assertEquals(2.0,vectorInMainCoordinateSystem.zComponent)
    }
}