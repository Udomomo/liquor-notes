package com.udomomo.liquornotes.usecases

import com.udomomo.liquornotes.controllers.ReviewResponse
import com.udomomo.liquornotes.controllers.TagResponse
import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.Id
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class GetReviewUseCase(
    private val reviewRepository: ReviewRepository,
    private val tagRepository: TagRepository,
) {
    fun execute(userId: String, reviewId: String): ReviewResponse? {
        val review = reviewRepository.findBy(Id(userId), Id(reviewId)) ?: return null
        val tags = tagRepository.listBy(review.tagIds)

        return ReviewResponse(
            review.id.value,
            review.userId.value,
            review.title,
            review.content,
            review.star.value,
            review.tagIds.map { tagId ->
                TagResponse(
                    tagId.value,
                    tags.find { tag -> tag.id == tagId }?.name ?: "",
                )
            },
        )
    }
}
