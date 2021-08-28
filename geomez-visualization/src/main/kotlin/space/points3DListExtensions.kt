package plane


import org.jzy3d.chart.AWTChart
import org.jzy3d.chart.ChartLauncher
import org.jzy3d.colors.Color
import org.jzy3d.plot3d.primitives.LineStrip
import org.jzy3d.plot3d.rendering.canvas.Quality
import space.Points3DList
import space.elements.Point3D
import space.toCoord3D
import space.toPlotPoint

fun List<Point3D>.plot(width: Double = 1.0, color: Color = Color.BLUE) {
    val chart = AWTChart(Quality.Fastest)

    this.forEach { point3D ->
        chart.scene.graph.add(point3D.toPlotPoint(color = color, width = width))
    }

    ChartLauncher.openChart(chart)
}

fun Points3DList.plot(width: Double = 1.0, color: Color = Color.BLUE) {
    val chart = AWTChart(Quality.Fastest)

    val lines = LineStrip()
    lines.addAll(this.points.map { it.toPlotPoint(color = color, width = width) })
    lines.wireframeWidth = width.toFloat()

    chart.add(lines)

    ChartLauncher.openChart(chart)
}
