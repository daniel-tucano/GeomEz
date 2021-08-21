package extensions

import org.ejml.simple.SimpleMatrix

operator fun SimpleMatrix.times(matrix: SimpleMatrix): SimpleMatrix {
    return this.mult(matrix)
}

operator fun SimpleMatrix.component1(): Double {
    return this[0]
}

operator fun SimpleMatrix.component2(): Double {
    return this[1]
}

operator fun SimpleMatrix.component3(): Double {
    return this[2]
}

// TODO("Add operations with Entities2D and 3D, such as add, subtract and multiply that returns Entities2D and 3D,
//  checking if its a possible operation given the matrix sizes")