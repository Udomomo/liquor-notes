package com.udomomo.liquornotes.domains

import org.springframework.stereotype.Repository

@Repository
interface TagRepository {
    fun findByNames(names: List<String>): List<Tag>

    fun save(tags: List<Tag>)
}
