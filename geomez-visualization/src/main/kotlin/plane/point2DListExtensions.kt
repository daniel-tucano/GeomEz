package plane

import jetbrains.datalore.vis.swing.jfx.DefaultPlotPanelJfx
import jetbrains.letsPlot.geom.geomLine
import jetbrains.letsPlot.ggplot
import jetbrains.letsPlot.intern.toSpec
import plane.elements.Point2D
import java.awt.Dimension
import java.awt.GridLayout
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.SwingUtilities
import javax.swing.WindowConstants.EXIT_ON_CLOSE

fun List<Point2D>.plot() {
    val plot = ggplot(mapOf("x" to this.map { it.x }, "y" to this.map { it.y })) {
        x = "x"
        y = "y"
    } + geomLine(linetype = "dashed")

    val window = JFrame("Points plot")
    window.defaultCloseOperation = EXIT_ON_CLOSE
//    window.contentPane.layout = BoxLayout(window.contentPane, BoxLayout.Y_AXIS)

    // Add plot panel
    val plotContainerPanel = JPanel(GridLayout())
    window.contentPane.add(plotContainerPanel)

    plotContainerPanel.add(DefaultPlotPanelJfx(
        processedSpec = plot.toSpec(),
        preserveAspectRatio = false,
        preferredSizeFromPlot = false,
        repaintDelay = 10
    ) {})

    plotContainerPanel.parent?.revalidate()

    SwingUtilities.invokeLater {
        window.pack()
        window.size = Dimension(850, 400)
        window.setLocationRelativeTo(null)
        window.isVisible = true
    }
}