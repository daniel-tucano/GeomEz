package plane

import io.github.danielTucano.matplotlib.Axes
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.pyplot.*
import io.github.danielTucano.matplotlib.show
import io.github.danielTucano.python.pythonExecution
import plane.elements.Vector2D
import kotlin.math.abs

fun Vector2D.plot() {
    pythonExecution {
        this.addPlotCommands()
        xlim((position.x - abs(x))*0.5, (position.x + abs(x))*1.5)
        ylim((position.y - abs(y))*0.5, (position.y + abs(y))*1.5)
        grid()
        show()
    }
}

fun Vector2D.addPlotCommands(figure: Figure? = null, axes: Axes? = null): Pair<Figure, Axes> {
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
    ax.quiver(
        listOf(this.position.x),
        listOf(this.position.y),
        listOf(this.x),
        listOf(this.y),
        scale = 1.0,
        scale_units = Axes.QuiverScaleUnitsOptions.xy,
        angles = Axes.QuiverAnglesOptions.xy
    )
    return fig to ax
}