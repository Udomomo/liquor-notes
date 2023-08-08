package com.udomomo.liquornotes.infrastructure.entities

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime

object TagTable: IdTable<String>("tags") {
    override val id: Column<EntityID<String>> = ReviewTable.varchar("id", 26).entityId()
    val name = ReviewTable.varchar("name", 50)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}
class TagEntity(id: EntityID<String>): StringEntity(id) {
    companion object: StringEntityClass<TagEntity>(TagTable)

    var name by TagTable.name
    var createdAt by ReviewTable.createdAt
    var updatedAt by ReviewTable.updatedAt
}
