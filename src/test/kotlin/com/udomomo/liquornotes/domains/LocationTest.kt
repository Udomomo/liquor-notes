package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LocationTest {
    @Test
    fun `should fail creating location with too long name`() {
        // Arrange
        val id = Id("testId")
        val name = "location01".repeat(5) + "a"

        // Do
        // Verify
        assertThrows<IllegalArgumentException> { Location.of(id, name) }
    }
}
