package space

import org.jzy3d.colors.Color
import org.jzy3d.maths.Coord3d
import org.jzy3d.plot3d.primitives.Point
import space.elements.Point3D

fun Point3D.toCoord3D(): Coord3d = Coord3d(this.x, this.y, this.z)
fun Point3D.toPlotPoint(color: Color = Color.BLUE, width: Double = 1.0): Point = Point(this.toCoord3D(), color, width.toFloat())