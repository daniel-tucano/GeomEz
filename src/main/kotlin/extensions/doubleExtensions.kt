package extensions

import kotlin.math.abs

/**
 * Does an equality assertion but with a delta threshold
 */
fun Double.equalsDelta(other: Double, delta: Double = 1e-9): Boolean = abs(this - other) < delta