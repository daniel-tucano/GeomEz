package plane

import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.pyplot.grid
import io.github.danielTucano.matplotlib.pyplot.subplots
import io.github.danielTucano.python.pythonExecution
import plane.elements.Point2D
import plane.elements.xValues
import plane.elements.yValues

fun List<Point2D>.plot(kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null) {
    pythonExecution {
        this.addPlotCommands(kwargs = kwargs)
        grid()
        show()
    }
}

fun List<Point2D>.addPlotCommands(figure: Figure? = null, axes: Axes? = null, kwargs: Map<Line2D.Line2DArgs, KwargValue>? = null): Pair<Figure, Axes> {
    lateinit var fig: Figure
    lateinit var ax: Axes
    when {
        (figure == null && axes == null) -> {
            val (fig2, ax2) = subplots()
            fig = fig2
            ax = ax2
        }
        (figure == null && axes != null) -> {
            fig = figure()
            ax = axes
        }
        (figure != null && axes == null) -> {
            fig = figure
            ax = figure.add_subplot()
        }
        else -> {
            fig = figure!!
            ax = axes!!
        }
    }
    ax.plot(x = this.xValues, y = this.yValues, kwargs = kwargs)
    return fig to ax
}