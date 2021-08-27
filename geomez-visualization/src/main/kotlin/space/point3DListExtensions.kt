package plane

import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.ChartLauncher
import org.jzy3d.chart.factories.AWTPainterFactory
import org.jzy3d.chart.factories.ChartFactory
import org.jzy3d.plot3d.rendering.canvas.Quality
import space.elements.Point3D
import space.toPlotPoint

fun List<Point3D>.plot() {
    val chartFactory = ChartFactory(AWTPainterFactory())
    val chart = AWTChart(chartFactory, Quality.Fastest())

    this.forEach {point3D ->
        chart.scene.graph.add(point3D.toPlotPoint())
    }

    ChartLauncher.openChart(chart)

}