package com.udomomo.liquornotes.domains

import com.udomomo.liquornotes.ids.Id
import org.springframework.stereotype.Repository

@Repository
interface TagRepository {
    fun listBy(tagIds: List<Id>): List<Tag>

    fun findByNames(names: List<String>): List<Tag>

    fun save(tags: List<Tag>)
}
