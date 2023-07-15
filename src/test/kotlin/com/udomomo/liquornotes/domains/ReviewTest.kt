package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ReviewTest {
    @Test
    fun `should create review successfully`() {
        // Arrange
        val userId = Id("testId") // TODO: use mock
        val title = "title".repeat(20)
        val contents = "contents".repeat(125)
        val star = Star.of(5.0)

        // Do
        val review = Review.of(userId, title, contents, star, listOf(Tag.of("tag01"), Tag.of("tag02")))

        // Verify
        assertEquals(title, review.title)
    }

    @Test
    fun `should fail creating review with too long title`() {
        // Arrange
        val userId = Id("testId") // TODO: use mock
        val title = "title".repeat(20) + "a"
        val contents = "contents".repeat(125)
        val star = Star.of(5.0)

        // Do
        val error =
            assertThrows<IllegalArgumentException> {
                Review.of(
                    userId,
                    title,
                    contents,
                    star,
                    listOf(Tag.of("tag01"), Tag.of("tag02")),
                )
            }

        // verify
        assertEquals("title is too long", error.message)
    }

    @Test
    fun `should fail creating review with too long contents`() {
        // Arrange
        val userId = Id("testId") // TODO: use mock
        val title = "title".repeat(20)
        val contents = "contents".repeat(125) + "a"
        val star = Star.of(5.0)

        // Do
        val error =
            assertThrows<IllegalArgumentException> {
                Review.of(
                    userId,
                    title,
                    contents,
                    star,
                    listOf(Tag.of("tag01"), Tag.of("tag02")),
                )
            }

        // verify
        assertEquals("contents is too long", error.message)
    }

    @Test
    fun `should fail creating review with too many tags`() {
        // Arrange
        val userId = Id("testId") // TODO: use mock
        val title = "title".repeat(20)
        val contents = "contents".repeat(125)
        val star = Star.of(5.0)
        val tags = (1..11).map { Tag.of("tag$it") }

        // Do
        val error = assertThrows<IllegalArgumentException> { Review.of(userId, title, contents, star, tags) }

        // verify
        assertEquals("tag is too many", error.message)
    }
}
