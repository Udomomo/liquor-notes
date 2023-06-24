package com.udomomo.liquornotes.domains

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TagTest {
    @Test
    fun `should create tag successfully`() {
        // Arrange
        val name = "tag01".repeat(10)

        // Do
        val tag = Tag.of(name)

        // Verify
        assertEquals(name, tag.name)
    }

    @Test
    fun `should fail creating tag with too long name`() {
        // Arrange
        val name = "tag01".repeat(10) + "a"

        // Do
        val error = assertThrows<IllegalArgumentException> { Tag.of(name) }

        // verify
        assertEquals("name is too long", error.message)
    }
}
