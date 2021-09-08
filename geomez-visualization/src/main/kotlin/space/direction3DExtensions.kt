package plane

import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.*
import io.github.danielTucano.python.pythonExecution
import space.elements.Direction3D

fun Direction3D.plot() {
    pythonExecution {
        val (_, ax) = this.addPlotCommands()
        xlabel("X")
        ylabel("Y")
        ax.set_zlabel("Z")
        xlim(-1.0, 1.0)
        ylim(-1.0, 1.0)
        ax.set_zlim3d(-1.0, 1.0)
        show()
    }
}

fun Direction3D.addPlotCommands(figure: Figure? = null, axes: Axes3D? = null, kwargs:  Map<KwargKey, KwargValue>? = null): Pair<Figure, Axes3D> {
    val fig = when(figure) {
        null -> figure()
        else -> figure
    }
    val ax = when(axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }
    ax.quiver(
        listOf(0.0),
        listOf(0.0),
        listOf(0.0),
        listOf(this.x),
        listOf(this.y),
        listOf(this.z),
        kwargs = kwargs
    )
    return fig to ax
}