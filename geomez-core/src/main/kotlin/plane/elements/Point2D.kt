package plane.elements

import extensions.equalsDelta
import extensions.times
import plane.CoordinateSystem2D
import space.CoordinateSystem3D
import space.elements.Point3D
import space.elements.Vector3D
import units.Angle
import utils.rotationMatrix2D
import kotlin.math.pow

class Point2D(
    override val x: Double,
    override val y: Double
) : Entity2D {

    constructor(pair: Pair<Double, Double>) : this(pair.first, pair.second)

    val distanceFromOrigin: Double
        get() = kotlin.math.sqrt(x.pow(2) + y.pow(2))

    val toVector3D
        get() = Vector3D(x, y, 0.0)

    fun toPoint3D(z: Double = 0.0) = Point3D(x, y, z)

    fun distanceBetween(point2D: Point2D): Double = (this - point2D).distanceFromOrigin

    override fun rotate(angle: Angle): Point2D = rotationMatrix2D(angle) * this

    override fun rotate(centerOfRotation: Point2D, angle: Angle): Point2D =
        rotationMatrix2D(angle, centerOfRotation) * this

    override fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): Point2D =
        to.affineMatrix.invert() * asWrittenIn.affineMatrix * this

    override fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Point3D =
        to.affineMatrix.invert() * asWrittenIn.affineMatrix * this.toPoint3D()

    //    Point operations

    override operator fun plus(entity2D: Entity2D): Point2D {
        return Point2D(x + entity2D.x, y + entity2D.y)
    }

    override operator fun minus(entity2D: Entity2D): Point2D {
        return Point2D(x - entity2D.x, y - entity2D.y)
    }

    operator fun times(entity2D: Entity2D): Point2D {
        return Point2D(x * entity2D.x, y * entity2D.y)
    }

    operator fun div(entity2D: Entity2D): Point2D {
        return Point2D(x / entity2D.x, y / entity2D.y)
    }

//    Scalar operations

    override operator fun plus(scalar: Double): Point2D {
        return Point2D(x + scalar, y + scalar)
    }

    override operator fun minus(scalar: Double): Point2D {
        return Point2D(x - scalar, y - scalar)
    }

    override operator fun times(scalar: Double): Point2D {
        return Point2D(x * scalar, y * scalar)
    }

    override operator fun div(scalar: Double): Point2D {
        return Point2D(x / scalar, y / scalar)
    }

    override operator fun unaryMinus(): Point2D {
        return Point2D(-x, -y)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point2D

        if (!x.equalsDelta(other.x)) return false
        if (!y.equalsDelta(other.y)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }

}