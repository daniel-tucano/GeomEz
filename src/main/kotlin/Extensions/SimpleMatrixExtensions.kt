package Extensions

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