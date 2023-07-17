package com.udomomo.liquornotes.ids

import de.huxhorn.sulky.ulid.ULID

object IdFactory {
    private val ulid = ULID()
    fun generate(): Id {
        return Id(ulid.nextULID())
    }
}

data class Id(val value: String)
