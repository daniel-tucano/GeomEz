package space.functions

interface Function3D {

    /**
     * Evaluate that function at given x and y values
     */
    operator fun invoke(x: Double, y: Double): Double
}