package plane

import io.github.danielTucano.matplotlib.Axes
import io.github.danielTucano.matplotlib.AxesBase
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.pyplot.grid
import io.github.danielTucano.matplotlib.pyplot.subplots
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import plane.elements.xValues
import plane.elements.yValues

fun Polygon2D.plot() {
    pythonExecution {
        val (_, ax) = this.addPlotCommands()
        ax.set_aspect(AxesBase.AspectOptions.equal, AxesBase.AjustableOptions.datalim)
        grid()
        show()
    }
}

fun Polygon2D.addPlotCommands(figure: Figure? = null, axes: Axes? = null): Pair<Figure, Axes> {
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
    ax.plot(pointsClosedPolygon.xValues, pointsClosedPolygon.yValues)
    return fig to ax
}