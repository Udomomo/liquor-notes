package com.udomomo.liquornotes.controllers

import com.udomomo.liquornotes.usecases.CreateLocationRequest
import com.udomomo.liquornotes.usecases.CreateReviewRequest
import com.udomomo.liquornotes.usecases.CreateReviewUseCase
import com.udomomo.liquornotes.usecases.CreateTagRequest
import com.udomomo.liquornotes.usecases.DeleteReviewUseCase
import com.udomomo.liquornotes.usecases.GetReviewResponse
import com.udomomo.liquornotes.usecases.GetReviewUseCase
import com.udomomo.liquornotes.usecases.ListReviewsResponse
import com.udomomo.liquornotes.usecases.ListReviewsUseCase
import com.udomomo.liquornotes.usecases.UpdateLocationRequest
import com.udomomo.liquornotes.usecases.UpdateReviewRequest
import com.udomomo.liquornotes.usecases.UpdateReviewUseCase
import com.udomomo.liquornotes.usecases.UpdateTagRequest
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
@RequestMapping("api/{userId}")
class ReviewRestController(
    private val listReviewsUseCase: ListReviewsUseCase,
    private val getReviewUseCase: GetReviewUseCase,
    private val createReviewUseCase: CreateReviewUseCase,
    private val updateReviewUseCase: UpdateReviewUseCase,
    private val deleteReviewUseCase: DeleteReviewUseCase,
) {
    @GetMapping("reviews")
    fun list(
        @PathVariable("userId") userId: String,
    ): List<ListReviewsResponse> {
        return listReviewsUseCase.execute(userId)
    }

    @GetMapping("review/{reviewId}")
    fun get(
        @PathVariable("userId") userId: String,
        @PathVariable("reviewId") reviewId: String,
    ): GetReviewResponse? {
        return getReviewUseCase.execute(userId, reviewId)
    }

    @PostMapping("review")
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
                location = if (request.location == null) {
                    null
                } else {
                    CreateLocationRequest(
                        id = request.location.id,
                        name = request.location.name!!,
                    )
                },
            ),
        )
    }

    @PutMapping("review/{reviewId}")
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
                location = if (request.location == null) {
                    null
                } else {
                    UpdateLocationRequest(
                        id = request.location.id,
                        name = request.location.name!!,
                    )
                },
            ),
        )
    }

    @DeleteMapping("review/{reviewId}")
    fun delete(
        @PathVariable("userId") userId: String,
        @PathVariable("reviewId") reviewId: String,
    ) {
        deleteReviewUseCase.execute(userId, reviewId)
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

    val location: LocationRequestBody?,
)

data class TagRequestBody(
    val id: String?,

    @NotBlank
    @Size(max = 50)
    val name: String?,
)

data class LocationRequestBody(
    val id: String?,

    @NotBlank
    @Size(max = 50)
    val name: String?,
)
