package com.udomomo.liquornotes.security

import com.udomomo.liquornotes.infrastructure.entities.TagTable
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.select
import org.springframework.stereotype.Repository

@Repository
class UserRepository {
    fun findByUsername(username: String): LoginUser? {
        return UserTable.select { UserTable.username eq username }
            .map { LoginUser(it[UserTable.username], it[UserTable.password]) }
            .firstOrNull()
    }
}

object UserTable : IdTable<String>("users") {
    override val id: Column<EntityID<String>> = TagTable.varchar("id", 26).entityId()
    val username = TagTable.varchar("name", 50)
    val password = TagTable.varchar("name", 128)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}
