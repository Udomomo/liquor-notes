package com.udomomo.liquornotes.usecases

import com.udomomo.liquornotes.domains.Location
import com.udomomo.liquornotes.domains.LocationRepository
import com.udomomo.liquornotes.domains.Review
import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.domains.Star
import com.udomomo.liquornotes.domains.Tag
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.Id
import com.udomomo.liquornotes.ids.IdFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class UpdateReviewRequest(
    val userId: String,
    val reviewId: String,
    val title: String,
    val content: String,
    val star: Double,
    val tags: List<UpdateTagRequest>,
    val location: UpdateLocationRequest?,
)

data class UpdateTagRequest(
    val id: String?,
    val name: String,
)

data class UpdateLocationRequest(
    val id: String?,
    val name: String,
)

@Service
@Transactional
class UpdateReviewUseCase(
    private val reviewRepository: ReviewRepository,
    private val tagRepository: TagRepository,
    private val locationRepository: LocationRepository,
) {
    fun execute(request: UpdateReviewRequest) {
        // 既存のreviewがあるか確認する。なければ400を返す。
        // TODO: 適切なカスタム例外を作る
        val review = reviewRepository.findBy(Id(request.userId), Id(request.reviewId))
            ?: throw IllegalArgumentException()

        val newTags = request.tags
            .filter { it.id == null }
            .map { Tag.of(IdFactory.generate(), it.name) }
        tagRepository.save(newTags)

        val newLocation = request.location
            ?.takeIf { it.id == null }
            ?.let { Location.of(IdFactory.generate(), it.name) }
        if (newLocation != null) {
            locationRepository.save(newLocation)
        }

        // リクエストに含まれるタグのIDを抽出し、新規タグのものは新たに生成されたIDで上書きする。
        val tagIds = request.tags.mapNotNull { it.id }.map { Id(it) } + newTags.map { it.id }
        val locationId = if (request.location?.id != null) Id(request.location.id) else newLocation?.id

        val updatedReview = Review.of(
            id = review.id,
            userId = review.userId,
            title = request.title,
            content = request.content,
            star = Star.of(request.star),
            tagIds = tagIds,
            locationId = locationId,
        )

        reviewRepository.update(updatedReview)
    }
}
