package com.udomomo.liquornotes.usecases

import com.udomomo.liquornotes.domains.LocationRepository
import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.Id
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class ListReviewsResponse(
    val id: String,
    val userId: String,
    val title: String,
    val content: String,
    val star: Double,
    val tags: List<ListTagResponse>,
    val location: ListLocationResponse?,
)

data class ListTagResponse(
    val id: String,
    val name: String,
)

data class ListLocationResponse(
    val id: String,
    val name: String,
)

@Service
@Transactional(readOnly = true)
class ListReviewsUseCase(
    private val reviewRepository: ReviewRepository,
    private val tagRepository: TagRepository,
    private val locationRepository: LocationRepository,
) {
    fun execute(userId: String): List<ListReviewsResponse> {
        val reviews = reviewRepository.listBy(Id(userId))
        val tags = tagRepository.listBy(reviews.flatMap { it.tagIds })
        val locations = locationRepository.listBy(reviews.mapNotNull { it.locationId })

        return reviews.map {
            ListReviewsResponse(
                it.id.value,
                it.userId.value,
                it.title,
                it.content,
                it.star.value,
                it.tagIds.map { tagId ->
                    ListTagResponse(
                        tagId.value,
                        tags.find { tag -> tag.id == tagId }?.name ?: "",
                    )
                },
                if (it.locationId == null) {
                    null
                } else {
                    ListLocationResponse(
                        it.locationId.value,
                        locations.find { location -> location.id == it.locationId }?.name ?: "",
                    )
                },
            )
        }
    }
}
