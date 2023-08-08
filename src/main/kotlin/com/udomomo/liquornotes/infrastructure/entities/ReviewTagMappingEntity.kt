package com.udomomo.liquornotes.infrastructure.entities

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.javatime.datetime

object ReviewTagMappingTable : IdTable<String>("review_tag_mappings") {
    override val id = varchar("id", 26).entityId()
    val reviewId = varchar("review_id", 26)
    val tagId = varchar("tag_id", 26)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}
class ReviewTagMappingEntity(id: EntityID<String>) : StringEntity(id) {
    companion object : StringEntityClass<ReviewTagMappingEntity>(ReviewTagMappingTable)

    var reviewId by ReviewTagMappingTable.reviewId
    var tagId by ReviewTagMappingTable.tagId
    var createdAt by ReviewTable.createdAt
    var updatedAt by ReviewTable.updatedAt
}
