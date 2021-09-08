package plane

import io.github.danielTucano.matplotlib.Axes
import io.github.danielTucano.matplotlib.AxesBase
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.*
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import plane.elements.Direction2D

fun Direction2D.plot() {
    pythonExecution {
        val (_, ax) = this.addPlotCommands()
        ax.set_aspect(AxesBase.AspectOptions.equal, AxesBase.AjustableOptions.box)
        xlim(-1.0, 1.0)
        ylim(-1.0, 1.0)
        grid()
        show()
    }
}

fun Direction2D.addPlotCommands(figure: Figure? = null, axes: Axes? = null): Pair<Figure, Axes> {
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
    val quiver = ax.quiver(
        listOf(0.0),
        listOf(0.0),
        listOf(this.x),
        listOf(this.y),
        scale = 1.0,
        scale_units = Axes.QuiverScaleUnitsOptions.xy,
        angles = Axes.QuiverAnglesOptions.xy
    )
    quiver.set_zorder(3.0)
    return fig to ax
}