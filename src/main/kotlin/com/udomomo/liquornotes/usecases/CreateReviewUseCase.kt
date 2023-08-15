package com.udomomo.liquornotes.usecases

import com.udomomo.liquornotes.domains.Review
import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.domains.Star
import com.udomomo.liquornotes.domains.Tag
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.Id
import com.udomomo.liquornotes.ids.IdFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

data class CreateReviewRequest(
    val userId: String,
    val title: String,
    val content: String,
    val star: Double,
    val tags: List<CreateTagRequest>,
)

data class CreateTagRequest(
    val id: String?,
    val name: String,
)

@Service
@Transactional
class CreateReviewUseCase(
    private val reviewRepository: ReviewRepository,
    private val tagRepository: TagRepository,
) {
    fun execute(request: CreateReviewRequest) {
        val newTags = request.tags
            .filter { it.id == null }
            .map { Tag.of(IdFactory.generate(), it.name) }
        tagRepository.save(newTags)

        // リクエストに含まれるタグのIDを抽出し、新規タグのものは新たに生成されたIDで上書きする。
        val tagIds = request.tags.mapNotNull { it.id }.map { Id(it) } + newTags.map { it.id }

        val review = Review.of(
            id = IdFactory.generate(),
            userId = Id(request.userId),
            title = request.title,
            content = request.content,
            star = Star.of(request.star),
            tagIds = tagIds,
        )

        reviewRepository.save(review)
    }
}
