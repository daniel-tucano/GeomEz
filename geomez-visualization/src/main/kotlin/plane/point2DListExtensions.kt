package plane

import com.github.sh0nk.matplotlib4j.Plot
import org.jzy3d.colors.Color
import plane.elements.Point2D

fun List<Point2D>.plot(width: Int = 1, color: Color = Color.BLUE) {
    val plot = Plot.create()
    plot.plot().add(this.map { it.x }, this.map { it.y})
    plot.show()
}