package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Review
import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.ids.Id
import com.udomomo.liquornotes.infrastructure.entities.ReviewEntity
import com.udomomo.liquornotes.infrastructure.entities.ReviewTagMappingTable
import com.udomomo.liquornotes.infrastructure.entities.TagEntity
import com.udomomo.liquornotes.infrastructure.entities.TagTable
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insertIgnore
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class ReviewRepositoryImpl : ReviewRepository {
    override fun listBy(userId: Id): List<Review> {
        TODO("Not yet implemented")
    }

    override fun post(review: Review) {
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
        }
    }
}
