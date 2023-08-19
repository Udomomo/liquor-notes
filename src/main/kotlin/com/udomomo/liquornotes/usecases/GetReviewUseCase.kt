package com.udomomo.liquornotes.usecases

import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.Id
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class GetReviewResponse(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val star: Double,
    val tags: List<GetTagResponse>,
)

data class GetTagResponse(
    val id: String,
    val name: String,
)

@Service
@Transactional(readOnly = true)
class GetReviewUseCase(
    private val reviewRepository: ReviewRepository,
    private val tagRepository: TagRepository,
) {
    fun execute(userId: String, reviewId: String): GetReviewResponse? {
        val review = reviewRepository.findBy(Id(userId), Id(reviewId)) ?: return null
        val tags = tagRepository.listBy(review.tagIds)

        return GetReviewResponse(
            review.id.value,
            review.userId.value,
            review.title,
            review.content,
            review.star.value,
            review.tagIds.map { tagId ->
                GetTagResponse(
                    tagId.value,
                    tags.find { tag -> tag.id == tagId }?.name ?: "",
                )
            },
        )
    }
}
