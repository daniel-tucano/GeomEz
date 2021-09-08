package space

import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.figure
import io.github.danielTucano.matplotlib.pyplot.grid
import io.github.danielTucano.matplotlib.pyplot.xlim
import io.github.danielTucano.matplotlib.pyplot.ylim
import io.github.danielTucano.python.pythonExecution
import kotlin.math.min

fun CoordinateSystem3D.plot() {
    pythonExecution {
        val (_, ax) = addPlotCommands()
        xlim(min(-(this.origin.x + 2),-2.0), maxOf((this.origin.x + 2),2.0))
        ylim(min(-(this.origin.y + 2),-2.0), maxOf((this.origin.y + 2),2.0))
        ax.set_zlim3d(min(-(this.origin.z + 2),-2.0), maxOf((this.origin.z + 2),2.0))
        grid()
        show()
    }
}

fun CoordinateSystem3D.addPlotCommands(figure: Figure? = null, axes: Axes3D? = null): Pair<Figure, Axes3D> {
    val fig = when(figure) {
        null -> figure()
        else -> figure
    }
    val ax = when(axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }

    ax.quiver(
        listOf(this.origin.x),
        listOf(this.origin.y),
        listOf(this.origin.z),
        listOf(this.xDirection.x),
        listOf(this.xDirection.y),
        listOf(this.xDirection.z),
        kwargs = mapOf(Line2D.Line2DArgs.color to KwargValue.Quoted("r"))
    )
    ax.quiver(
        listOf(this.origin.x),
        listOf(this.origin.y),
        listOf(this.origin.z),
        listOf(this.yDirection.x),
        listOf(this.yDirection.y),
        listOf(this.yDirection.z),
        kwargs = mapOf(Line2D.Line2DArgs.color to KwargValue.Quoted("g"))
    )
    ax.quiver(
        listOf(this.origin.x),
        listOf(this.origin.y),
        listOf(this.origin.z),
        listOf(this.zDirection.x),
        listOf(this.zDirection.y),
        listOf(this.zDirection.z),
        kwargs = mapOf(Line2D.Line2DArgs.color to KwargValue.Quoted("b"))
    )
    return (fig to ax)
}