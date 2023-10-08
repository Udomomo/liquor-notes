package com.udomomo.liquornotes.infrastructure.entities

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

// ExposedのEntityは複合Primary keyのテーブルに対応していないため、Tableのみ作成してDSLを用いる。
object ReviewLocationMappingTable : Table("review_location_mappings") {
    val reviewId = varchar("review_id", 26)
    val locationId = varchar("location_id", 26)
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}
