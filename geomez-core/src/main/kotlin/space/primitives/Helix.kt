package space.primitives

import space.Curve3D
import space.elements.CylindricalCoordinate
import space.elements.Point3D
import units.Angle

/**
 * Represents a Helix that start at Z = 0 and grows towards z direction
 */
class Helix(
    nPoints: UInt = 100.toUInt(),
    radius: Double,
    zDistance: Double,
    turns: Double = 1.0,
    /**
     * Positive in the anti-clockwise direction
     */
    startingAngle: Angle = Angle.Degrees(0.0),
    startingZ: Double = 0.0,
    antiClockwise: Boolean = true
) : Curve3D(
    constructHelixPoints(nPoints.toInt(), radius, zDistance, turns, startingAngle, startingZ, antiClockwise)
) {

    companion object {
        private fun constructHelixPoints(
            nPoints: Int,
            radius: Double,
            zDistance: Double,
            turns: Double,
            startingAngle: Angle,
            startingZ: Double,
            antiClockwise: Boolean
        ): List<Point3D> {
            val pointsList = mutableListOf<Point3D>()
            val dZ = zDistance / nPoints
            val dTheta = Angle.Degrees(360.0) * turns / nPoints.toDouble()
            val thetaDirection = if (antiClockwise) 1 else -1
            for (i in 0..nPoints) {
                pointsList.add(
                    CylindricalCoordinate(
                        radius = radius,
                        angle = (startingAngle + dTheta * i.toDouble() * thetaDirection.toDouble()),
                        z = startingZ + dZ * i
                    ).toPoint3D()
                )
            }
            return pointsList.toList()
        }
    }



}