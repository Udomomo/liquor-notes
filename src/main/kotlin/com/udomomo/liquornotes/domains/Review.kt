package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id

class Review private constructor(
    val userId: Id,
    val title: String,
    val contents: String,
    val star: Star,
    val tags: List<Tag>,
) {
    companion object {
        fun of(
            userId: Id,
            title: String,
            contents: String,
            star: Star,
            tags: List<Tag>,
        ): Review {
            if (title.length > 100) throw IllegalArgumentException("title is too long")
            if (contents.length > 1000) throw IllegalArgumentException("contents is too long")
            if (tags.size > 10) throw IllegalArgumentException("tag is too many")

            return Review(userId, title, contents, star, tags)
        }
    }
}
