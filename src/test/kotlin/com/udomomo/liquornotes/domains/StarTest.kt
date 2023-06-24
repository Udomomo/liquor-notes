package com.udomomo.liquornotes.domains

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class StarTest {
    @ParameterizedTest
    @ValueSource(doubles = [0.0, 5.1, 10.0])
    fun `should create star successfully`(value: Double) {
        // Arrange
        // Do
        val star = Star.of(value)

        // Verify
        assertEquals(value, star.value)
    }

    @ParameterizedTest
    @ValueSource(doubles = [-0.1, 10.1])
    fun `should fail creating star with out of range value`(value: Double) {
        // Arrange
        // Do
        val error = assertThrows<IllegalArgumentException> { Star.of(value) }

        // verify
        assertEquals("value is out of range", error.message)
    }

    @ParameterizedTest
    @ValueSource(doubles = [5.01, 5.001, 5.0001])
    fun `should fail creating star with wrong format`(value: Double) {
        // Arrange
        // Do
        val error = assertThrows<IllegalArgumentException> { Star.of(value) }

        // verify
        assertEquals("value should have exactly one decimal place", error.message)
    }
}
