package com.udomomo.liquornotes.controllers

import com.udomomo.liquornotes.usecases.CreateReviewRequest
import com.udomomo.liquornotes.usecases.CreateReviewUseCase
import com.udomomo.liquornotes.usecases.CreateTagRequest
import com.udomomo.liquornotes.usecases.GetReviewResponse
import com.udomomo.liquornotes.usecases.GetReviewUseCase
import com.udomomo.liquornotes.usecases.ListReviewResponse
import com.udomomo.liquornotes.usecases.ListReviewsUseCase
import com.udomomo.liquornotes.usecases.UpdateReviewRequest
import com.udomomo.liquornotes.usecases.UpdateReviewUseCase
import com.udomomo.liquornotes.usecases.UpdateTagRequest
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReviewRestController(
    private val listReviewsUseCase: ListReviewsUseCase,
    private val getReviewUseCase: GetReviewUseCase,
    private val createReviewUseCase: CreateReviewUseCase,
    private val updateReviewUseCase: UpdateReviewUseCase,
) {
    @GetMapping("{userId}/reviews")
    fun list(
        @PathVariable("userId") userId: String,
    ): List<ListReviewResponse> {
        return listReviewsUseCase.execute(userId)
    }

    @GetMapping("{userId}/review/{reviewId}")
    fun get(
        @PathVariable("userId") userId: String,
        @PathVariable("reviewId") reviewId: String,
    ): GetReviewResponse? {
        return getReviewUseCase.execute(userId, reviewId)
    }

    @PostMapping("{userId}/review")
    fun post(
        @PathVariable("userId") userId: String,
        @RequestBody @Validated request: PostRequestBody,
    ) {
        createReviewUseCase.execute(
            CreateReviewRequest(
                userId = userId,
                title = request.title!!,
                content = request.content!!,
                star = request.star,
                tags = request.tags.map {
                    CreateTagRequest(
                        id = it.id,
                        name = it.name!!,
                    )
                },
            ),
        )
    }

    @PutMapping("{userId}/review/{reviewId}")
    fun put(
        @PathVariable("userId") userId: String,
        @PathVariable("reviewId") reviewId: String,
        @RequestBody @Validated request: PostRequestBody,
    ) {
        updateReviewUseCase.execute(
            UpdateReviewRequest(
                userId = userId,
                reviewId = reviewId,
                title = request.title!!,
                content = request.content!!,
                star = request.star,
                tags = request.tags.map {
                    UpdateTagRequest(
                        id = it.id,
                        name = it.name!!,
                    )
                },
            ),
        )
    }
}

data class PostRequestBody(
    @NotBlank
    val userId: String?,

    @NotNull
    @Size(max = 100)
    val title: String?,

    @NotNull
    @Size(max = 1000)
    val content: String?,

    // hibernateによるバリデーションはサポートされていないので、ドメインでバリデーションを行う。
    val star: Double,

    val tags: List<TagRequestBody>,
)

data class TagRequestBody(
    val id: String?,

    @NotBlank
    @Size(max = 50)
    val name: String?,
)
