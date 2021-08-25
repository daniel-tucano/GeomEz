package plane.elements

import units.Angle
import utils.cos
import utils.sin

class PolarCoordinate(val radius: Double, val angle: Angle) {

    fun toPoint2D(): Point2D = Point2D( radius * cos(angle), radius * sin(angle))

}