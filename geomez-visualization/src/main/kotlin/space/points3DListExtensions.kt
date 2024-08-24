package plane


import extensions.xValues
import extensions.yValues
import extensions.zValues
import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.python.pythonExecution
import space.Points3DList
import space.elements.Point3D

fun List<Point3D>.plot(kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null) {
    pythonExecution {
        this.addPlotCommands(kwargs = kwargs)
        show()
    }
}

fun Points3DList.plot(kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null) {
    pythonExecution {
        this.addPlotCommands(kwargs = kwargs)
        show()
    }
}

fun List<Point3D>.addPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null
): Pair<Figure, Axes3D> {
    val fig = when (figure) {
        null -> figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }
    ax.plot(xValues, yValues, zValues, kwargs = kwargs)
    return fig to ax
}

fun List<Point3D>.addScatterPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    kwargs: Map<Axes3D.Scatter3DKwargsKeys, KwargValue>? = null
): Pair<Figure, Axes3D> {

    val fig = when (figure) {
        null -> figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }

    ax.scatter(this.xValues, this.yValues, this.zValues, kwargs = kwargs)

    return fig to ax
}

fun Points3DList.addPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null
): Pair<Figure, Axes3D> {
    val fig = when (figure) {
        null -> figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }
    ax.plot(xValues, yValues, zValues, kwargs = kwargs)
    return fig to ax
}