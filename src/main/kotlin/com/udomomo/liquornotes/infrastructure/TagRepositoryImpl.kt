package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Tag
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.Id
import org.springframework.stereotype.Repository

@Repository
class TagRepositoryImpl : TagRepository {
    override fun listBy(tagIds: List<Id>): List<Tag> {
        TODO("Not yet implemented")
    }

    override fun findByNames(names: List<String>): List<Tag> {
        TODO("Not yet implemented")
    }

    override fun save(tags: List<Tag>) {
        TODO("Not yet implemented")
    }
}
