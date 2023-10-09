package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id

interface LocationRepository {
    fun findById(id: Id): Location?

    fun listBy(locationIds: List<Id>): List<Location>

    fun save(location: Location)
}
