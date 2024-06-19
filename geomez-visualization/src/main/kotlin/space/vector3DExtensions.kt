package space

import io.github.danielTucano.matplotlib.*
import io.github.danielTucano.matplotlib.pyplot.*
import io.github.danielTucano.python.pythonExecution
import space.elements.Vector3D
import kotlin.math.abs

fun Vector3D.plot() {
    pythonExecution {
        val (_, ax) = this.addPlotCommands()
        xlabel("X")
        ylabel("Y")
        ax.set_zlabel("Z")
        xlim((position.x - abs(x)) * 0.5, (position.x + abs(x)) * 1.5)
        ylim((position.y - abs(y)) * 0.5, (position.y + abs(y)) * 1.5)
        ax.set_zlim3d((position.z - abs(z)) * 0.5, (position.z + abs(z)) * 1.5)
        show()
    }
}

fun Vector3D.addPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    kwargs: Map<KwargKey, KwargValue>? = null
): Pair<Figure, Axes3D> {
    val fig = when (figure) {
        null -> figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }
    ax.quiver(
        listOf(this.position.x),
        listOf(this.position.y),
        listOf(this.position.z),
        listOf(this.x),
        listOf(this.y),
        listOf(this.z),
        kwargs = kwargs
    )
    return fig to ax
}
