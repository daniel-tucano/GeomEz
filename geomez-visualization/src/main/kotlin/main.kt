import extensions.mapIndexed
import io.github.danielTucano.matplotlib.Axes3D
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import space.functions.Function3D
import utils.linspace
import utils.meshgrid

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
//    curve3D.plot()


//    val pointsList = listOf(Point2D(0.0, 1.0), Point2D(1.0, 1.5), Point2D(2.0, 0.5))
//
//    pointsList.plot()

//    val vector2D = Vector2D(1.0,1.0, Point2D(1.0,0.0))
//    vector.plot()

//    val vector3D = Vector3D(1.0,0.0,1.0, Point3D(1.0,1.0,1.0))
//    vector.plot()

//    Direction3D.MAIN_X_DIRECTION.plot()

//    Direction2D.MAIN_X_DIRECTION.plot()

//    CoordinateSystem2D.MAIN_2D_COORDINATE_SYSTEM.plot()

//    CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM.plot()

//    val controlPoints = listOf(Point2D(1.0, 1.0), Point2D(1.5, 2.0), Point2D(2.5,2.5), Point2D(3.5, 0.2))
//    val bezierCurve = BezierCurve(controlPoints)
//    bezierCurve.plot()

//    val (convexPolygon, _) = convexHull(
//        listOf(
//            Point2D(0.0, 0.0),
//            Point2D(1.0, 1.0),
//            Point2D(1.5, 0.5),
//            Point2D(1.7, 0.7),
//            Point2D(2.0, 1.0),
//            Point2D(2.5, 0.0)
//        )
//    )
//    convexPolygon.plot()

//    val points = listOf(Point2D(1.0, 1.0), Point2D(1.5, 2.0), Point2D(1.7, 1.85), Point2D(2.0, 3.2))
//    val cubicSpline = CubicSpline(points)
//    cubicSpline.plot()

//    pythonExecution {
//        val (fig2D, ax2D) = curve.addPlotCommands()
//        pointsList.addPlotCommands(fig2D,ax2D)
//        vector2D.addPlotCommands(fig2D,ax2D)
//        Direction2D.MAIN_X_DIRECTION.addPlotCommands(fig2D,ax2D)
//        CoordinateSystem2D.MAIN_2D_COORDINATE_SYSTEM.addPlotCommands(fig2D,ax2D)
//        bezierCurve.addPlotCommands(fig2D,ax2D)
//        convexPolygon.addPlotCommands(fig2D,ax2D)
//        grid()
//
//        val (fig3D, ax3D) = curve3D.addPlotCommands()
//        vector3D.addPlotCommands(fig3D,ax3D)
//        Direction3D.MAIN_X_DIRECTION.addPlotCommands(fig3D,ax3D)
//        CoordinateSystem3D.MAIN_3D_COORDINATE_SYSTEM.addPlotCommands(fig3D,ax3D)
//        show()
//    }

    val (X,Y) = meshgrid(linspace(-10.0,10.0,40),linspace(-10.0,10.0,40))
    val function3D = object: Function3D {
        override fun invoke(x: Double, y: Double): Double  = x*x + y*y

    }
    val Z = X.mapIndexed { index, _ ->
        function3D(X[index],Y[index])
    }
    pythonExecution {
        val fig = figure()
        val ax = fig.add_subplot(Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        ax.plot_surface(X,Y,Z)
        show()
    }

}

