package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class TagTest {
    @Test
    fun `should create tag successfully`() {
        // Arrange
        val id = Id("testId")
        val name = "tag01".repeat(10)

        // Do
        val tag = Tag.of(id, name)

        // Verify
        assertEquals(name, tag.name)
    }

    @Test
    fun `should fail creating tag with too long name`() {
        // Arrange
        val id = Id("testId")
        val name = "tag01".repeat(10) + "a"

        // Do
        val error = assertThrows<IllegalArgumentException> { Tag.of(id, name) }

        // verify
        assertEquals("name is too long", error.message)
    }
}
