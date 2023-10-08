package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id

interface LocationRepository {
    fun findById(id: Id): Location?

    fun save(location: Location)
}
