package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Review
import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.domains.Star
import com.udomomo.liquornotes.ids.Id
import com.udomomo.liquornotes.infrastructure.entities.ReviewEntity
import com.udomomo.liquornotes.infrastructure.entities.ReviewTable
import com.udomomo.liquornotes.infrastructure.entities.ReviewTagMappingTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ReviewRepositoryImpl : ReviewRepository {
    override fun listBy(userId: Id): List<Review> {
        val reviewEntities = ReviewEntity.find { ReviewTable.userId eq userId.value }
        if (reviewEntities.count() == 0L) return emptyList()

        val reviewTagMappings = ReviewTagMappingTable.select {
            ReviewTagMappingTable.reviewId inList reviewEntities.map { entity -> entity.id.value }
        }.map { Pair(it[ReviewTagMappingTable.reviewId], it[ReviewTagMappingTable.tagId]) }

        return reviewEntities.map { reviewEntity ->
            val tagIds = reviewTagMappings
                .filter { it.first == reviewEntity.id.value }
                .map { Id(it.second) }
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

    override fun findBy(userId: Id, reviewId: Id): Review? {
        val reviewEntity = ReviewEntity
            .find { ReviewTable.userId eq userId.value and(ReviewTable.id eq reviewId.value) }
            .singleOrNull()
            ?: return null

        val reviewTagMappings = ReviewTagMappingTable
            .select { ReviewTagMappingTable.reviewId eq reviewEntity.id.value }
            .map { Pair(it[ReviewTagMappingTable.reviewId], it[ReviewTagMappingTable.tagId]) }

        val tagIds = reviewTagMappings
            .filter { it.first == reviewEntity.id.value }
            .map { Id(it.second) }

        return Review.of(
            id = Id(reviewEntity.id.value),
            userId = Id(reviewEntity.userId),
            title = reviewEntity.title,
            content = reviewEntity.content,
            star = Star.of(reviewEntity.star),
            tagIds = tagIds,
        )
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

    override fun update(review: Review) {
        val createdAt = LocalDateTime.now()
        val updatedAt = LocalDateTime.now()
        ReviewTable.update({ ReviewTable.id eq review.id.value }) {
            it[title] = review.title
            it[content] = review.content
            it[star] = review.star.value
            it[this.updatedAt] = updatedAt
        }

        ReviewTagMappingTable.deleteWhere { reviewId eq review.id.value }
        ReviewTagMappingTable.batchInsert(review.tagIds) { tagId ->
            this[ReviewTagMappingTable.reviewId] = review.id.value
            this[ReviewTagMappingTable.tagId] = tagId.value
            this[ReviewTagMappingTable.createdAt] = createdAt
            this[ReviewTagMappingTable.updatedAt] = updatedAt
        }
    }

    override fun delete(userId: Id, reviewId: Id) {
        ReviewTable.deleteWhere { ReviewTable.userId eq userId.value and(ReviewTable.id eq reviewId.value) }
        ReviewTagMappingTable.deleteWhere { ReviewTagMappingTable.reviewId eq reviewId.value }
    }
}
