package Plane

import Space.CoordinateSystem3D
import Units.Angle
import Units.cos
import Units.sin

class PolarCoordinate(val radius: Double, val angle: Angle) {

    fun toPoint2D(): Point2D = Point2D( radius * cos(angle), radius * sin(angle))

}