package com.udomomo.liquornotes.controllers

import com.udomomo.liquornotes.usecases.CreateReviewRequest
import com.udomomo.liquornotes.usecases.CreateReviewUseCase
import com.udomomo.liquornotes.usecases.TagRequest
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReviewRestController(private val createReviewUseCase: CreateReviewUseCase) {
    @PostMapping("/review")
    fun post(
        @RequestBody @Validated request: PostRequestBody,
    ) {
        createReviewUseCase.execute(
            CreateReviewRequest(
                userId = request.userId!!,
                title = request.title!!,
                content = request.content!!,
                star = request.star,
                tags = request.tags.map {
                    TagRequest(
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
