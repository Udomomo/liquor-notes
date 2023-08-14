package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Review
import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.domains.Star
import com.udomomo.liquornotes.ids.Id
import com.udomomo.liquornotes.infrastructure.entities.ReviewEntity
import com.udomomo.liquornotes.infrastructure.entities.ReviewTable
import com.udomomo.liquornotes.infrastructure.entities.ReviewTagMappingEntity
import com.udomomo.liquornotes.infrastructure.entities.ReviewTagMappingTable
import org.jetbrains.exposed.sql.batchInsert
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ReviewRepositoryImpl : ReviewRepository {
    override fun listBy(userId: Id): List<Review> {
        val reviewEntities = ReviewEntity.find { ReviewTable.userId eq userId.value }
        val reviewTagMappings = ReviewTagMappingEntity.find {
            ReviewTagMappingTable.reviewId inList reviewEntities.map { it.id.value }
        }

        return reviewEntities.map { reviewEntity ->
            val tagIds = reviewTagMappings
                .filter { it.reviewId == reviewEntity.id.value }
                .map { Id(it.tagId) }
            Review.of(
                id = Id(reviewEntity.id.value),
                userId = Id(reviewEntity.userId),
                title = reviewEntity.title,
                content = reviewEntity.content,
                star = Star.of(reviewEntity.star),
                tagIds = tagIds,
            )
        }
    }

    override fun save(review: Review) {
        val createdAt = LocalDateTime.now()
        val updatedAt = LocalDateTime.now()

        ReviewEntity.new(id = review.id.value) {
            userId = review.userId.value
            title = review.title
            content = review.content
            star = review.star.value
            this.createdAt = createdAt
            this.updatedAt = updatedAt
        }

        // tagをreviewに紐付ける
        ReviewTagMappingTable.batchInsert(review.tagIds) { tagId ->
            this[ReviewTagMappingTable.reviewId] = review.id.value
            this[ReviewTagMappingTable.tagId] = tagId.value
            this[ReviewTagMappingTable.createdAt] = createdAt
            this[ReviewTagMappingTable.updatedAt] = updatedAt
        }
    }
}
