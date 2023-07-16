package com.udomomo.liquornotes.infrastructure.entities

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime

object ReviewTable: IdTable<String>("reviews") {
    override val id: Column<EntityID<String>> = varchar("id", 26).entityId()
    val userId = varchar("user_id", 26)
    val title = varchar("title", 100)
    val content = varchar("content", 1000)
    val stars = integer("stars")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}

class ReviewEntity(id: EntityID<String>): StringEntity(id) {
    companion object: StringEntityClass<ReviewEntity>(ReviewTable)

    var userId by ReviewTable.userId
    var title by ReviewTable.title
    var content by ReviewTable.content
    var stars by ReviewTable.stars
    var createdAt by ReviewTable.createdAt
    var updatedAt by ReviewTable.updatedAt
}
