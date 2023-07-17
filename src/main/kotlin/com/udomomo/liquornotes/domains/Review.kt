package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id

class Review private constructor(
    val id: Id,
    val userId: Id,
    val title: String,
    val content: String,
    val star: Star,
    val tags: List<Tag>,
) {
    companion object {
        fun of(
            id: Id,
            userId: Id,
            title: String,
            content: String,
            star: Star,
            tags: List<Tag>,
        ): Review {
            if (title.length > 100) throw IllegalArgumentException("title is too long")
            if (content.length > 1000) throw IllegalArgumentException("content is too long")
            if (tags.size > 10) throw IllegalArgumentException("tag is too many")

            return Review(id, userId, title, content, star, tags)
        }
    }
}
