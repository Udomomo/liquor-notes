package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id

class Review private constructor(
    val id: Id,
    val userId: Id,
    val title: String,
    val content: String,
    val star: Star,
    val tagIds: List<Id>,
) {
    companion object {
        fun of(
            id: Id,
            userId: Id,
            title: String,
            content: String,
            star: Star,
            tagIds: List<Id>,
        ): Review {
            if (title.length > 100) throw IllegalArgumentException("title is too long: $title")
            if (content.length > 1000) throw IllegalArgumentException("content is too long: $content")
            if (tagIds.size > 10) throw IllegalArgumentException("tag is too many: $tagIds")
            if (tagIds.size != tagIds.distinct().size) {
                throw IllegalArgumentException("Multiple same tag exists: $tagIds")
            }

            return Review(id, userId, title, content, star, tagIds)
        }
    }
}
