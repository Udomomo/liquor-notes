package com.udomomo.liquornotes.usecases

import com.udomomo.liquornotes.domains.ReviewRepository
import com.udomomo.liquornotes.ids.Id
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeleteReviewUseCase(
    private val reviewRepository: ReviewRepository,
) {
    fun execute(userId: String, reviewId: String) {
        reviewRepository.delete(Id(userId), Id(reviewId))
    }
}
