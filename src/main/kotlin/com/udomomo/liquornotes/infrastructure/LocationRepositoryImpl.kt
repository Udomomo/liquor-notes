package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Location
import com.udomomo.liquornotes.domains.LocationRepository
import com.udomomo.liquornotes.ids.Id
import com.udomomo.liquornotes.infrastructure.entities.LocationEntity
import com.udomomo.liquornotes.infrastructure.entities.LocationTable
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class LocationRepositoryImpl : LocationRepository {
    override fun listBy(locationIds: List<Id>): List<Location> {
        return LocationTable
            .select { LocationTable.id inList locationIds.map { it.value } }
            .map {
                Location.of(
                    id = Id(it[LocationTable.id].value),
                    name = it[LocationTable.name],
                )
            }
    }

    override fun findById(id: Id): Location? {
        val entity = LocationEntity.find { LocationTable.id eq id.value }
            .singleOrNull()

        return entity?.let { Location.of(Id(it.id.value), it.name) }
    }

    override fun save(location: Location) {
        val createdAt = LocalDateTime.now()
        val updatedAt = LocalDateTime.now()

        LocationEntity.new(id = location.id.value) {
            name = location.name
            this.createdAt = createdAt
            this.updatedAt = updatedAt
        }
    }
}
