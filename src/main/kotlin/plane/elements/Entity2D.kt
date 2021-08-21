package plane.elements

import org.ejml.simple.SimpleMatrix
import plane.CoordinateSystem2D
import space.CoordinateSystem3D
import space.elements.Entity3D
import units.Angle

interface Entity2D {

    val x: Double
    val y: Double

    /**
     * Column matrix representation of that entity with its x and y components respectively
     */
    val matrix: SimpleMatrix
        get() = SimpleMatrix(
            arrayOf(
                doubleArrayOf(x),
                doubleArrayOf(y)
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
                doubleArrayOf(1.0)
            )
        )

    /**
     * Rotate entity in the anti-clockwise direction along the given center of rotation
     */
    fun rotate(centerOfRotation: Point2D, angle: Angle): Entity2D

    /**
     * Describe entity as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem2D, to: CoordinateSystem2D): Entity2D

    /**
     * Describe entity as if was written in the "asWrittenIn" coordinate system in terms of the "to" coordinate system
     */
    fun changeBasis(asWrittenIn: CoordinateSystem3D, to: CoordinateSystem3D): Entity3D

    // Two-dimensional Entity operations

    operator fun plus(entity2D: Entity2D): Entity2D

    operator fun minus(entity2D: Entity2D): Entity2D

    operator fun unaryMinus(): Entity2D

    //    Scalar operations

    operator fun plus(scalar: Double): Entity2D

    operator fun minus(scalar: Double): Entity2D

    operator fun times(scalar: Double): Entity2D

    operator fun div(scalar: Double): Entity2D

    // TODO("Add operations with SimpleMatrix, such as add, subtract and multiply that returns Entities2D")

    operator fun component1(): Double = x

    operator fun component2(): Double = y
}