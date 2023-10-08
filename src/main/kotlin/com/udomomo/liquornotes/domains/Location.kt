package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id

class Location private constructor(
    val id: Id,
    val name: String,
) {
    companion object {
        fun of(id: Id, name: String): Location {
            if (name.length > 50) throw IllegalArgumentException("location name is too long")

            return Location(id, name)
        }
    }
}
