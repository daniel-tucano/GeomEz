package space

import space.elements.Point3D
import space.elements.Vector3D

interface VectorField3D {

    operator fun invoke(x: Double, y: Double, z: Double): Vector3D

    operator fun invoke(point3D: Point3D): Vector3D {
        return this(point3D.x, point3D.y, point3D.z)
    }

    operator fun plus(vectorField3D: VectorField3D): VectorField3D {
        return object: VectorField3D {
            override fun invoke(x: Double, y: Double, z: Double): Vector3D {
                return this@VectorField3D(x,y,z) + vectorField3D(x,y,z)
            }
        }
    }

    operator fun minus(vectorField3D: VectorField3D): VectorField3D {
        return object: VectorField3D {
            override fun invoke(x: Double, y: Double, z: Double): Vector3D {
                return this@VectorField3D(x,y,z) - vectorField3D(x,y,z)
            }
        }
    }

    operator fun times(scalar: Double): VectorField3D {
        return object: VectorField3D {
            override fun invoke(x: Double, y: Double, z: Double): Vector3D {
                return this@VectorField3D(x,y,z) * scalar
            }
        }
    }

    operator fun div(scalar: Double): VectorField3D {
        return object: VectorField3D {
            override fun invoke(x: Double, y: Double, z: Double): Vector3D {
                return this@VectorField3D(x,y,z) / scalar
            }
        }
    }
}