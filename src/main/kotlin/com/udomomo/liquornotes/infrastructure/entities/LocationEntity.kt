package com.udomomo.liquornotes.infrastructure.entities

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime

object LocationTable : IdTable<String>("locations") {
    override val id: Column<EntityID<String>> = varchar("id", 26).entityId()
    val name = varchar("name", 50)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}
class LocationEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<LocationEntity>(LocationTable)

    var name by LocationTable.name
    var createdAt by LocationTable.createdAt
    var updatedAt by LocationTable.updatedAt
}
