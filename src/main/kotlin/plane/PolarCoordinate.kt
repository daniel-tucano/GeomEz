package plane

import units.Angle
import units.cos
import units.sin

class PolarCoordinate(val radius: Double, val angle: Angle) {

    fun toPoint2D(): Point2D = Point2D( radius * cos(angle), radius * sin(angle))

}