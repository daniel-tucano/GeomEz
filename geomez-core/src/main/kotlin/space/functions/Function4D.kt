package space.functions

interface Function4D {

    /**
     * Evaluate that function at given x, y and z values
     */
    operator fun invoke(x: Double, y: Double, z: Double): Double
}