package com.udomomo.liquornotes.domains

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ReviewTest {
    @Test
    fun `should create review successfully`() {
        // Arrange
        val title = "title".repeat(20)
        val contents = "contents".repeat(125)
        val star = Star.of(5.0)

        // Do
        val review = Review.of(title, contents, star, listOf(1L, 2L))

        // Verify
        assertEquals(title, review.title)
    }

    @Test
    fun `should fail creating review with too long title`() {
        // Arrange
        val title = "title".repeat(20) + "a"
        val contents = "contents".repeat(125)
        val star = Star.of(5.0)

        // Do
        val error = assertThrows<IllegalArgumentException> { Review.of(title, contents, star, listOf(1L, 2L)) }

        // verify
        assertEquals("title is too long", error.message)
    }

    @Test
    fun `should fail creating review with too long contents`() {
        // Arrange
        val title = "title".repeat(20)
        val contents = "contents".repeat(125) + "a"
        val star = Star.of(5.0)

        // Do
        val error = assertThrows<IllegalArgumentException> { Review.of(title, contents, star, listOf(1L, 2L)) }

        // verify
        assertEquals("contents is too long", error.message)
    }
}
