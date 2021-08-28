import org.jzy3d.colors.Color
import plane.Curve2D
import plane.Polygon2D
import plane.elements.Point2D
import plane.plot
import space.CoordinateSystem3D
import space.Curve3D
import space.elements.Direction3D
import units.Angle

fun main() {

//    val curve =
//        Polygon2D(
//            listOf(
//                Point2D(0.0, 1.0),
//                Point2D(1.0, 0.0),
//                Point2D(2.0, 1.0),
//                Point2D(1.0, 2.0)
//            )
//        )
//
//    val curve3D = Curve3D(
//        curve.changeBasis(
//            asWrittenIn = CoordinateSystem3D(
//                Direction3D.MAIN_X_DIRECTION.rotate(
//                    Direction3D.MAIN_Z_DIRECTION,
//                    Angle.Degrees(45.0)
//                ) as Direction3D,
//                Direction3D.MAIN_Z_DIRECTION,
//                Direction3D.MAIN_Y_DIRECTION.rotate(
//                    Direction3D.MAIN_Z_DIRECTION,
//                    Angle.Degrees(45.0)
//                ) as Direction3D
//            ),
//            to = CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM
//        )
//    )
//
//    curve3D.plot(color = Color.BLACK)


    val pointsList = listOf(Point2D(0.0, 1.0), Point2D(1.0, 1.5), Point2D(2.0, 0.5))

    pointsList.plot()
}