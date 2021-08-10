package Space

import Extensions.component1
import Extensions.component2
import Extensions.component3
import Extensions.times
import Units.Angle
import Utils.rotationMatrix

/**
 * Represent a direction (unit vector at the origin) in 3D space
 */
class Direction3D(xComponent: Double,yComponent: Double,zComponent: Double): Vector3DBase(xComponent,yComponent,zComponent) {
    init {
        super.xComponent /= module
        super.yComponent /= module
        super.zComponent /= module
    }

    companion object {
        val MAIN_X_DIRECTION = Direction3D(1.0,0.0,0.0)
        val MAIN_Y_DIRECTION = Direction3D(0.0,1.0,0.0)
        val MAIN_Z_DIRECTION = Direction3D(0.0,0.0,1.0)
    }

    /**
     * Rotate vector in the anti-clockwise direction along the given axis
     */
    fun rotate(axis: Direction3D, angle: Angle): Direction3D {
        val rotationMatrix = rotationMatrix(axis,angle)
        val (vX,vY,vZ) = rotationMatrix * this.matrix
        return Direction3D(vX,vY,vZ)
    }

    infix fun cross(direction: Direction3D): Direction3D {
        val (a1, a2, a3) = this
        val (b1, b2, b3) = direction

        return Direction3D(a2 * b3 - a3 * b2, a3 * b1 - a1 * b3, a1 * b2 - a2 * b1)
    }

    override fun unaryMinus(): Direction3D {
        return Direction3D(-xComponent,-yComponent,-zComponent)
    }
}