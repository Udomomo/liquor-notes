package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Tag
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.Id
import com.udomomo.liquornotes.infrastructure.entities.TagTable
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class TagRepositoryImpl : TagRepository {
    override fun listBy(tagIds: List<Id>): List<Tag> {
        return TagTable
            .select { TagTable.id inList tagIds.map { it.value } }
            .map {
                Tag.of(
                    id = Id(it[TagTable.id].value),
                    name = it[TagTable.name],
                )
            }
    }

    override fun findByNames(names: List<String>): List<Tag> {
        TODO("Not yet implemented")
    }

    override fun save(tags: List<Tag>) {
        val createdAt = LocalDateTime.now()
        val updatedAt = LocalDateTime.now()

        TagTable.batchInsert(tags) {
            this[TagTable.id] = it.id.value
            this[TagTable.name] = it.name
            this[TagTable.createdAt] = createdAt
            this[TagTable.updatedAt] = updatedAt
        }
    }
}
