package com.udomomo.liquornotes.usecases

import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.Id
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ListReviewsUseCase(
    private val reviewRepository: ReviewRepository,
    private val tagRepository: TagRepository,
) {
    fun execute(userId: String): List<ReviewResponse> {
        val reviews = reviewRepository.listBy(Id(userId))
        val tags = tagRepository.listBy(reviews.flatMap { it.tagIds }.distinct())

        return reviews.map {
            ReviewResponse(
                it.id.value,
                it.userId.value,
                it.title,
                it.content,
                it.star.value,
                it.tagIds.map { tagId ->
                    TagResponse(
                        tagId.value,
                        tags.find { tag -> tag.id == tagId }?.name ?: "",
                    )
                },
            )
        }
    }
}

data class ReviewResponse(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val star: Double,
    val tags: List<TagResponse>,
)

data class TagResponse(
    val id: String,
    val name: String,
)
