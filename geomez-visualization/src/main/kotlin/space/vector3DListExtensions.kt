package space

import io.github.danielTucano.matplotlib.Axes3D
import io.github.danielTucano.matplotlib.Figure
import io.github.danielTucano.matplotlib.KwargKey
import io.github.danielTucano.matplotlib.KwargValue
import space.elements.Vector3D


fun List<Vector3D>.addPlotCommands(
    figure: Figure? = null,
    axes: Axes3D? = null,
    kwargs: Map<KwargKey, KwargValue>? = null,
    scale: Double = 1.0
): Pair<Figure, Axes3D> {
    val fig = when (figure) {
        null -> io.github.danielTucano.matplotlib.pyplot.figure()
        else -> figure
    }
    val ax = when (axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }

    ax.quiver(
        this.map { it.position.x },
        this.map { it.position.y },
        this.map { it.position.z },
        this.map { it.x * scale},
        this.map { it.y * scale},
        this.map { it.z * scale},
        kwargs = kwargs
    )
    return fig to ax
}