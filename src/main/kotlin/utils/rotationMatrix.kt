package utils

import units.Angle
import org.ejml.simple.SimpleMatrix
import plane.elements.Point2D
import space.elements.Direction3D
import space.elements.Point3D
import units.cos
import units.sin
import kotlin.math.pow

/**
 * Returns the 3x3 rotation matrix correspondent to a counter-clockwise rotation of angle units around the provided axis
 * following the right-hand rule.
 *
 * NOTE: The rotation its performed with the axis at the origin
 *
 * @param axis  axis of rotation
 * @param angle counter-clockwise angle of desired rotation about axis of rotation
 * @return      3x3 matrix that if multiplied by an 3D entity matrix, such as an Point3D or Vector3D matrix, rotate that
 *              entity around the origin
 */
fun rotationMatrix3D(axis: Direction3D, angle: Angle): SimpleMatrix {
    // Axis components
    val(uX,uY,uZ) = axis

    return SimpleMatrix(
        arrayOf(
            doubleArrayOf(cos(angle) + uX.pow(2) * (1 - cos(angle)), uX * uY * (1 - cos(angle)) - uZ * sin(angle), uX * uZ * (1 - cos(angle)) + uY * sin(angle)),
            doubleArrayOf(uY * uX * (1 - cos(angle)) + uZ * sin(angle), cos(angle) + uY.pow(2) * (1 - cos(angle)), uY * uZ * (1 - cos(angle)) - uX * sin(angle)),
            doubleArrayOf(uZ * uX * (1 - cos(angle)) - uY * sin(angle), uZ * uY * (1 - cos(angle)) + uX * sin(angle), cos(angle) + uZ.pow(2) * (1 - cos(angle)))
        )
    )
}

/**
 * Returns the 4x4 affine rotation matrix correspondent to a counter-clockwise rotation of angle units around the provided axis
 * following the right-hand rule.
 *
 * NOTE: The rotation its performed with the axis at the provided centerOfRotation
 *
 * NOTE: This matrix must be multiplied by a point3D affine matrix to have the correct effect * @param axis of rotation
 *
 * @param axis              Axis of rotation
 * @param angle             Counter-clockwise angle of desired rotation about axis of rotation
 * @param centerOfRotation  Point used as center of the rotation
 * @return                  4x4 matrix that if multiplied by an 3D entity affine matrix, such as an Point3D or Vector3D
 *                          affine matrix, rotate that entity around the center of rotation
 */
fun rotationMatrix3D(axis: Direction3D, angle: Angle, centerOfRotation: Point3D): SimpleMatrix {
    // Axis components
    val (uX,uY,uZ) = axis
    // Coordinates of the center of rotation
    val (xCR,yCR,zCR) = centerOfRotation
    // Elements of the rotation matrix around the origin
    val (a1,a2,a3) = doubleArrayOf(cos(angle) + uX.pow(2) * (1 - cos(angle)), uX * uY * (1 - cos(angle)) - uZ * sin(angle), uX * uZ * (1 - cos(angle)) + uY * sin(angle))
    val (b1,b2,b3) = doubleArrayOf(uY * uX * (1 - cos(angle)) + uZ * sin(angle), cos(angle) + uY.pow(2) * (1 - cos(angle)), uY * uZ * (1 - cos(angle)) - uX * sin(angle))
    val (c1,c2,c3) = doubleArrayOf(uZ * uX * (1 - cos(angle)) - uY * sin(angle), uZ * uY * (1 - cos(angle)) + uX * sin(angle), cos(angle) + uZ.pow(2) * (1 - cos(angle)))

    return SimpleMatrix(
        arrayOf(
            doubleArrayOf(a1,a2,a3,  xCR * (1-a1) - yCR * a2     - zCR * a3     ),
            doubleArrayOf(b1,b2,b3, -xCR * b1     - yCR * (1-b2) - zCR * b3     ),
            doubleArrayOf(c1,c2,c3,  xCR * c1     - yCR * c2     - zCR * (1-c3) ),
            doubleArrayOf(0.0, 0.0, 0.0, 1.0)
        )
    )
}

/**
 * Returns the 2x2 rotation matrix correspondent to a counter-clockwise rotation of angle units around the Z axis
 * following the right-hand rule.
 *
 * NOTE: The rotation its performed around the origin
 *
 * @param angle Counter-clockwise angle of desired rotation about origin
 * @return      2x2 matrix that if multiplied by an 2D entity matrix, such as an Point3D or Vector3D
 *              matrix, rotate that entity around the origin
 */
fun rotationMatrix2D(angle: Angle): SimpleMatrix {
    return SimpleMatrix(
        arrayOf(
            doubleArrayOf( cos(angle) , sin(angle)),
            doubleArrayOf(-sin(angle) , cos(angle))
        )
    )
}

/**
 * Returns the 3x3 affine rotation matrix correspondent to a counter-clockwise rotation of angle units around the Z axis
 * following the right-hand rule.
 *
 * NOTE: The rotation its performed around the centerOfRotation provided.
 *
 * NOTE: This matrix must be multiplied by a point2D affine matrix to have the correct effect
 *
 * @param angle             Counter-clockwise angle of desired rotation about the center of rotation
 * @param centerOfRotation  Point used as center of the rotation
 * @return                  3x3 matrix that if multiplied by an 2D entity affine matrix, such as an Point3D or Vector3D
 *                          affine matrix, rotate that entity around the center of rotation
 */
fun rotationMatrix2D(angle: Angle, centerOfRotation: Point2D): SimpleMatrix {
    // Coordinates of the center of rotation
    val (xCR,yCR) = centerOfRotation

    return SimpleMatrix(
        arrayOf(
            doubleArrayOf( cos(angle) , sin(angle), xCR * (1 - cos(angle))  - yCR * sin(angle)          ),
            doubleArrayOf(-sin(angle) , cos(angle), xCR * sin(angle)        + yCR * (1 - cos(angle))    ),
            doubleArrayOf(    0.0     ,    0.0    ,                       1.0                           )
        )
    )
}