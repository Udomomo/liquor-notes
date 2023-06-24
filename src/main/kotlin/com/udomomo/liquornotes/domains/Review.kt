package com.udomomo.liquornotes.domains

class Review private constructor(
    val title: String,
    val contents: String,
    val star: Star,
    val tagIds: List<Long>,
) {
    companion object {
        fun of(
            title: String,
            contents: String,
            star: Star,
            tagIds: List<Long>,
        ): Review {
            if (title.length > 100) throw IllegalArgumentException("title is too long")
            if (contents.length > 1000) throw IllegalArgumentException("contents is too long")

            return Review(title, contents, star, tagIds)
        }
    }
}
