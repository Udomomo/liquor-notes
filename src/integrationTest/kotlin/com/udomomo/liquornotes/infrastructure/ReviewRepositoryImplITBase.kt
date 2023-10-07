package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Review
import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.domains.Star
import com.udomomo.liquornotes.ids.IdFactory
import com.udomomo.liquornotes.testhelper.ITBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class ReviewRepositoryImplITBase : ITBase() {
    @Autowired
    private lateinit var reviewRepository: ReviewRepository

    @Test
    fun `list review successfully`() {
        val id = IdFactory.generate()
        val userId = IdFactory.generate()
        val tagId = IdFactory.generate()
        val review = Review.of(
            id = id,
            userId = userId,
            title = "title",
            content = "content",
            star = Star.of(3.5),
            tagIds = listOf(tagId),
        )

        reviewRepository.save(review)

        val result = reviewRepository.listBy(userId)

        assertEquals(1, result.size)
        assertEquals(result[0].id, id)
        assertEquals(result[0].userId, userId)
        assertEquals(result[0].title, "title")
        assertEquals(result[0].content, "content")
        assertEquals(result[0].star, Star.of(3.5))
        assertEquals(result[0].tagIds, listOf(tagId))
    }

    @Test
    fun `find review successfully`() {
        val id = IdFactory.generate()
        val userId = IdFactory.generate()
        val tagId = IdFactory.generate()
        val review = Review.of(
            id = id,
            userId = userId,
            title = "title",
            content = "content",
            star = Star.of(3.5),
            tagIds = listOf(tagId),
        )

        reviewRepository.save(review)

        val result = reviewRepository.findBy(userId, id)

        assertNotNull(result)
        assertEquals(result!!.id, id)
        assertEquals(result.userId, userId)
        assertEquals(result.title, "title")
        assertEquals(result.content, "content")
        assertEquals(result.star, Star.of(3.5))
        assertEquals(result.tagIds, listOf(tagId))
    }

    @Test
    fun `update review successfully`() {
        val id = IdFactory.generate()
        val userId = IdFactory.generate()
        val tagId = IdFactory.generate()
        val review = Review.of(
            id = id,
            userId = userId,
            title = "title",
            content = "content",
            star = Star.of(3.5),
            tagIds = listOf(tagId),
        )

        reviewRepository.save(review)

        val updatedReview = Review.of(
            id = id,
            userId = userId,
            title = "title",
            content = "new content",
            star = Star.of(3.5),
            tagIds = listOf(tagId),
        )
        reviewRepository.update(updatedReview)

        val result = reviewRepository.listBy(userId)

        assertEquals(1, result.size)
        assertEquals(result[0].id, review.id)
        assertEquals(result[0].userId, userId)
        assertEquals(result[0].title, "title")
        assertEquals(result[0].content, "new content")
        assertEquals(result[0].star, Star.of(3.5))
        assertEquals(result[0].tagIds, listOf(tagId))
    }
}
