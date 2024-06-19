package space

import io.github.danielTucano.matplotlib.Axes3D
import io.github.danielTucano.matplotlib.Colormap
import io.github.danielTucano.matplotlib.Figure
import org.ejml.simple.SimpleMatrix

fun ParametricSurface3D.addPlotCommands(
    T: SimpleMatrix,
    S: SimpleMatrix,
    figure: Figure? = null,
    axes: Axes3D? = null,
    colormap: Colormap
): Pair<Figure, Axes3D> {
    val fig = when(figure) {
        null -> io.github.danielTucano.matplotlib.pyplot.figure()
        else -> figure
    }
    val ax = when(axes) {
        null -> fig.add_subplot(projection = Figure.AddSubplotProjectionOptions.`3d`) as Axes3D
        else -> axes
    }

    val (X, Y, Z) = this(T, S)

    ax.plot_surface(X, Y, Z, colormap)

    return fig to ax
}