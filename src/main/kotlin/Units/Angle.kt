package Units

import java.lang.IllegalArgumentException
import kotlin.math.PI

sealed class Angle {
    abstract val value: Double

    abstract operator fun plus(value: Double): Angle
    abstract operator fun minus(value: Double): Angle
    abstract operator fun times(value: Double): Angle
    abstract operator fun div(value: Double): Angle

    abstract operator fun plus(angle: Angle): Angle
    abstract operator fun minus(angle: Angle): Angle
    abstract operator fun times(angle: Angle): Angle
    abstract operator fun div(angle: Angle): Angle

    data class Radians (override var value: Double): Angle() {
        fun toDegrees(): Degrees = Degrees(this.value * 180.0/PI)

        override operator fun plus(value: Double): Radians = Radians(this.value + value)
        override operator fun minus(value: Double): Radians = Radians(this.value - value)
        override operator fun times(value: Double): Radians = Radians(this.value * value)
        override operator fun div(value: Double): Radians = Radians(this.value / value)

        override operator fun plus(angle: Angle): Radians {
            return when (angle) {
                is Radians -> Radians(this.value + angle.value)
                is Degrees -> Radians(this.value + angle.value * PI/180)
            }
        }

        override operator fun minus(angle: Angle): Radians {
            return when (angle) {
                is Radians -> Radians(this.value - angle.value)
                is Degrees -> Radians(this.value - angle.value * PI/180)
            }
        }

        override operator fun times(angle: Angle): Radians {
            return when (angle) {
                is Radians -> Radians(this.value * angle.value)
                is Degrees -> Radians(this.value * angle.value * PI/180)
            }
        }

        override operator fun div(angle: Angle): Radians {
            return when (angle) {
                is Radians -> Radians(this.value * angle.value)
                is Degrees -> Radians(this.value * angle.value * PI/180)
            }
        }
    }
    data class Degrees (override var value: Double): Angle() {
        fun toRadians(): Radians = Radians(this.value * PI/180.0)

        override operator fun plus(value: Double): Degrees = Degrees(this.value + value)
        override operator fun minus(value: Double): Degrees = Degrees(this.value - value)
        override operator fun times(value: Double): Degrees = Degrees(this.value * value)
        override operator fun div(value: Double): Degrees = Degrees(this.value / value)

        override operator fun plus(angle: Angle): Degrees {
            return when (angle) {
                is Radians -> Degrees(this.value + angle.value * 180/PI)
                is Degrees -> Degrees(this.value + angle.value)
            }
        }

        override operator fun minus(angle: Angle): Degrees {
            return when (angle) {
                is Radians -> Degrees(this.value + angle.value * 180/PI)
                is Degrees -> Degrees(this.value + angle.value)
            }
        }

        override operator fun times(angle: Angle): Degrees {
            return when (angle) {
                is Radians -> Degrees(this.value + angle.value * 180/PI)
                is Degrees -> Degrees(this.value + angle.value)
            }
        }

        override operator fun div(angle: Angle): Degrees {
            return when (angle) {
                is Radians -> Degrees(this.value + angle.value * 180/PI)
                is Degrees -> Degrees(this.value + angle.value)
            }
        }
    }
}

