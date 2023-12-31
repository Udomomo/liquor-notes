package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TagTest {
    @Test
    fun `should fail creating tag with too long name`() {
        // Arrange
        val id = Id("testId")
        val name = "tag01".repeat(10) + "a"

        // Do
        // Verify
        assertThrows<IllegalArgumentException> { Tag.of(id, name) }
    }
}
