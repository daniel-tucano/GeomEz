package space

import org.ejml.simple.SimpleMatrix
import space.elements.Point3D
import space.elements.Vector3D
import space.functions.Function3D

open class ParametricSurface3D(
    val xParametricFunction: Function3D,
    val yParametricFunction: Function3D,
    val zParametricFunction: Function3D
) {

    fun numericalNormalDirection(t: Double, s: Double, delta: Double = 10.0e-5): Vector3D {
        val position = this(t, s)
        val tangentDirectionT = Vector3D(this(t + delta, s), position).direction
        val tangentDirectionS = Vector3D(this(t, s + delta), position).direction
        val normalDirection = tangentDirectionT.cross(tangentDirectionS)
        return Vector3D(normalDirection.x, normalDirection.y, normalDirection.z, position = position)
    }

    open operator fun invoke(x: Double, y: Double): Point3D {
        return Point3D(xParametricFunction(x, y), yParametricFunction(x, y), zParametricFunction(x, y))
    }

    operator fun invoke(T: SimpleMatrix, S: SimpleMatrix): Triple<SimpleMatrix, SimpleMatrix, SimpleMatrix> {

        val X = SimpleMatrix(T.numRows(), S.numCols())
        val Y = SimpleMatrix(T.numRows(), S.numCols())
        val Z = SimpleMatrix(T.numRows(), S.numCols())

        (0 until T.numRows()).forEach { rowIndex ->
            (0 until T.numCols()).forEach { colIndex ->
                val point = this(T[rowIndex, colIndex], S[rowIndex, colIndex])
                X[rowIndex, colIndex] = point.x
                Y[rowIndex, colIndex] = point.y
                Z[rowIndex, colIndex] = point.z
            }
        }

        return Triple(X, Y, Z)
    }
}