package plane

import io.github.danielTucano.matplotlib.Axes
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.pyplot.grid
import io.github.danielTucano.matplotlib.pyplot.subplots
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import plane.functions.CubicSpline
import utils.linspace

fun CubicSpline.plot() {
    pythonExecution {
        this.addPlotCommands()
        grid()
        show()
    }
}

fun CubicSpline.addPlotCommands(figure: Figure? = null, axes: Axes? = null, nPoints: Int = 100): Pair<Figure, Axes> {
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
    this(linspace(this.xPoints.first(), this.xPoints.last(), nPoints))
    ax.plot(this.xPoints, this.yPoints, "o")
    return fig to ax
}