package space.elements

import space.CoordinateSystem3D
import units.Angle
import org.ejml.simple.SimpleMatrix

interface Entity3D {
    var x: Double
    var y: Double
    var z: Double

    val matrix: SimpleMatrix
        get() = SimpleMatrix(
            arrayOf(
                doubleArrayOf(x),
                doubleArrayOf(y),
                doubleArrayOf(z)
            )
        )

    /**
     * Matrix used for affine transformations that perform scaling, translation and rotation
     */
    val affineMatrix: SimpleMatrix
        get() = SimpleMatrix(
            arrayOf(
                doubleArrayOf(x),
                doubleArrayOf(y),
                doubleArrayOf(z),
                doubleArrayOf(1.0)
            )
        )

    /**
     * Rotate point in the anti-clockwise direction along the given axis
     */
    fun rotate(axis: VectorialEntity3D, angle: Angle): Entity3D

    /**
     * Describe point as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Entity3D

    //    TridimentionalEntity operations

    operator fun plus(entity3D: Entity3D): Entity3D

    operator fun minus(entity3D: Entity3D): Entity3D

    operator fun unaryMinus(): Entity3D

    //    Scalar operations

    operator fun plus(scalar: Double): Entity3D

    operator fun minus(scalar: Double): Entity3D

    operator fun times(scalar: Double): Entity3D

    operator fun div(scalar: Double): Entity3D

    operator fun component1(): Double = x

    operator fun component2(): Double = y

    operator fun component3(): Double = z

}