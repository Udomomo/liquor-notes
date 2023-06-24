package com.udomomo.liquornotes.domains

class Tag private constructor(
    val name: String,
) {
    companion object {
        fun of(name: String): Tag {
            if (name.length > 50) throw IllegalArgumentException("name is too long")

            return Tag(name)
        }
    }
}
