package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id

class Tag private constructor(
    val id: Id,
    val name: String,
) {
    companion object {
        fun of(id: Id, name: String): Tag {
            if (name.length > 50) throw IllegalArgumentException("name is too long")

            return Tag(id, name)
        }
    }
}
