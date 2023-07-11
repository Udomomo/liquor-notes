package com.udomomo.liquornotes.ids

import de.huxhorn.sulky.ulid.ULID

object IdFactory {
    private val ulid = ULID()
    fun generate(): String {
        return ulid.nextULID()
    }
}
