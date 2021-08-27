package plane.functions

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import plane.elements.Point2D

internal class PolynomialTest {

    @Test
    fun `must evaluate polynomial correctly in specified point` () {
        val polynomial = Polynomial(listOf(1.0, 2.0, 3.0, 4.0))

        val point1 = polynomial(0.0)
        val point2 = polynomial(1.0)
        val point3 = polynomial(1.2)
        val point4 = polynomial(5.1)

        assertTrue(point1 == Point2D(0.0,1.0))
        assertTrue(point2 == Point2D(1.0,10.0))
        assertTrue(point3 == Point2D(1.2,14.632))
        assertTrue(point4 == Point2D(5.1,619.834))
    }

    @Test
    fun `must return the correct derivative function for a polynomial` () {
        val polynomial = Polynomial(listOf(1.0, 2.0, 3.0, 4.0))
        val derivative = polynomial.derivative()

        assertEquals(2, derivative.order)
        assertEquals(2.0, derivative.coefficients[0])
        assertEquals(6.0, derivative.coefficients[1])
        assertEquals(12.0, derivative.coefficients[2])

    }

    @Test
    fun `must integrate correctly between integration points` () {
        val polynomial = Polynomial(listOf(1.0, 2.0, 3.0, 4.0))
        val integral = polynomial.integrate(0.0,2.0)

        assertEquals(30.0, integral)
    }

    @Test fun `must add correctly two polynomials` () {
        val polynomial1 = Polynomial(listOf(1.0, 2.0, 3.0))
        val polynomial2 = Polynomial(listOf(4.0, 5.0))

        val addition1 = polynomial1 + polynomial2

        assertEquals(2, addition1.order)
        assertEquals(5.0, addition1.coefficients[0])
        assertEquals(7.0, addition1.coefficients[1])
        assertEquals(3.0, addition1.coefficients[2])
    }

    @Test
    fun `must return the correct polynomial resulting from a multiplication between polynomials` () {
        val polynomial1 = Polynomial(listOf(1.0, -1.0))
        val polynomial2 = Polynomial(listOf(1.0, 1.0))

        val multiplication11 = polynomial1 * polynomial1
        val multiplication12 = polynomial1 * polynomial2
        val multiplication22 = polynomial2 * polynomial2

        assertEquals(2, multiplication11.order)
        assertEquals(1.0, multiplication11.coefficients[0])
        assertEquals(-2.0, multiplication11.coefficients[1])
        assertEquals(1.0, multiplication11.coefficients[2])

        assertEquals(2, multiplication12.order)
        assertEquals(1.0, multiplication12.coefficients[0])
        assertEquals(0.0, multiplication12.coefficients[1])
        assertEquals(-1.0, multiplication12.coefficients[2])

        assertEquals(2, multiplication22.order)
        assertEquals(1.0, multiplication22.coefficients[0])
        assertEquals(2.0, multiplication22.coefficients[1])
        assertEquals(1.0, multiplication22.coefficients[2])
    }
}