package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ReviewTest {
    @Test
    fun `should create review successfully`() {
        // Arrange
        val id = Id("testId")
        val userId = Id("testId") // TODO: use mock
        val title = "title".repeat(20)
        val content = "contents".repeat(125)
        val star = Star.of(5.0)

        // Do
        val review = Review.of(
            id,
            userId,
            title,
            content,
            star,
            listOf(Id("tag01"), Id("tag02")),
            Id("location"),
        )

        // Verify
        assertEquals(title, review.title)
    }

    @Test
    fun `should fail creating review with too long title`() {
        // Arrange
        val id = Id("testId")
        val userId = Id("testId") // TODO: use mock
        val title = "title".repeat(20) + "a"
        val content = "contents".repeat(125)
        val star = Star.of(5.0)

        // Do
        val error =
            assertThrows<IllegalArgumentException> {
                Review.of(
                    id,
                    userId,
                    title,
                    content,
                    star,
                    listOf(Id("tag01"), Id("tag02")),
                    Id("location"),
                )
            }

        // verify
        assertEquals("title is too long: $title", error.message)
    }

    @Test
    fun `should fail creating review with too long contents`() {
        // Arrange
        val id = Id("testId")
        val userId = Id("testId") // TODO: use mock
        val title = "title".repeat(20)
        val content = "contents".repeat(125) + "a"
        val star = Star.of(5.0)

        // Do
        // Verify
        assertThrows<IllegalArgumentException> {
            Review.of(
                id,
                userId,
                title,
                content,
                star,
                listOf(Id("tag01"), Id("tag02")),
                Id("location"),
            )
        }
    }

    @Test
    fun `should fail creating review with too many tags`() {
        // Arrange
        val id = Id("testId")
        val userId = Id("testId") // TODO: use mock
        val title = "title".repeat(20)
        val content = "contents".repeat(125)
        val star = Star.of(5.0)
        val tags = (1..11).map { Id("tag$it") }

        // Do
        val error = assertThrows<IllegalArgumentException> { Review.of(id, userId, title, content, star, tags) }

        // verify
        assertEquals("tag is too many: $tags", error.message)
    }

    @Test
    fun `should fail creating review with same tags`() {
        // Arrange
        val id = Id("testId")
        val userId = Id("testId") // TODO: use mock
        val title = "title".repeat(20)
        val content = "contents".repeat(125)
        val star = Star.of(5.0)
        val tags = listOf(1, 2, 3, 1).map { Id("tag$it") }

        // Do
        val error = assertThrows<IllegalArgumentException> { Review.of(id, userId, title, content, star, tags) }

        // verify
        assertEquals("Multiple same tag exists: $tags", error.message)
    }
}
