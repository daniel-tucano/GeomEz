package space.elements

import org.ejml.simple.SimpleMatrix
import space.CoordinateSystem3D
import units.Angle

interface Entity3D {
    val x: Double
    val y: Double
    val z: Double

    /**
     * Column matrix representation of that entity with its x, y and z components respectively
     */
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
     * It's the same as 'matrix' but with an extra element at the end with value of 1
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
     * Rotate entity in the anti-clockwise direction along the given axis
     */
    fun rotate(axis: VectorialEntity3D, angle: Angle): Entity3D

    /**
     * Describe entity as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Entity3D

    // Three-dimensional Entity operations

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