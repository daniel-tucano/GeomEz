package extensions

import space.elements.Point3D

val List<Point3D>.xValues: List<Double>
    get() = this.map { it.x }
val List<Point3D>.yValues: List<Double>
    get() = this.map { it.y }
val List<Point3D>.zValues: List<Double>
    get() = this.map { it.z }