package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id

interface ReviewRepository {
    fun listBy(userId: Id): List<Review>
    fun findBy(userId: Id, reviewId: Id): Review?
    fun save(review: Review)
}
