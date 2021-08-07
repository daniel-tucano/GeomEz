package Units

import java.lang.IllegalArgumentException
import kotlin.math.PI

sealed class Angle {
    abstract val value: Double

    data class Radians (override var value: Double): Angle() {
        fun toDegrees(): Degrees = Degrees(this.value * 180.0/PI)

        operator fun plus(value: Double): Double = this.value + value
        operator fun minus(value: Double): Double = this.value - value
        operator fun times(value: Double): Double = this.value * value
        operator fun div(value: Double): Double = this.value / value

        operator fun plus(angle: Angle): Radians {
            return when (angle) {
                is Radians -> Radians(this.value + angle.value)
                is Degrees -> Radians(this.value + angle.value * PI/180)
            }
        }

        operator fun minus(angle: Angle): Angle {
            return when (angle) {
                is Radians -> Radians(this.value - angle.value)
                is Degrees -> Radians(this.value - angle.value * PI/180)
            }
        }

        operator fun times(angle: Angle): Angle {
            return when (angle) {
                is Radians -> Radians(this.value * angle.value)
                is Degrees -> Radians(this.value * angle.value * PI/180)
            }
        }

        operator fun div(angle: Angle): Angle {
            return when (angle) {
                is Radians -> Radians(this.value * angle.value)
                is Degrees -> Radians(this.value * angle.value * PI/180)
            }
        }
    }
    data class Degrees (override var value: Double): Angle() {
        fun toRadians(): Radians = Radians(this.value * PI/180.0)

        operator fun plus(value: Double): Double = this.value + value
        operator fun minus(value: Double): Double = this.value - value
        operator fun times(value: Double): Double = this.value * value
        operator fun div(value: Double): Double = this.value / value

        operator fun plus(angle: Angle): Angle {
            return when (angle) {
                is Radians -> Radians(this.value + angle.value * 180/PI)
                is Degrees -> Radians(this.value + angle.value)
            }
        }

        operator fun minus(angle: Angle): Angle {
            return when (angle) {
                is Radians -> Radians(this.value + angle.value * 180/PI)
                is Degrees -> Radians(this.value + angle.value)
            }
        }

        operator fun times(angle: Angle): Angle {
            return when (angle) {
                is Radians -> Radians(this.value + angle.value * 180/PI)
                is Degrees -> Radians(this.value + angle.value)
            }
        }

        operator fun div(angle: Angle): Angle {
            return when (angle) {
                is Radians -> Radians(this.value + angle.value * 180/PI)
                is Degrees -> Radians(this.value + angle.value)
            }
        }
    }
}

