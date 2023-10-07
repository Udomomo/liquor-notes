package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Tag
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.IdFactory
import com.udomomo.liquornotes.testhelper.ITBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class TagRepositoryImplIT : ITBase() {
    @Autowired
    private lateinit var tagRepository: TagRepository

    @Test
    fun `list tag successfully`() {
        val id1 = IdFactory.generate()
        val tag1 = Tag.of(id1, "tag1")
        val id2 = IdFactory.generate()
        val tag2 = Tag.of(id2, "tag2")

        tagRepository.save(listOf(tag1, tag2))

        val result = tagRepository.listBy(listOf(id1, id2))

        assertEquals(2, result.size)
        assertEquals(result[0].id, tag1.id)
        assertEquals(result[0].name, "tag1")
        assertEquals(result[1].id, tag2.id)
        assertEquals(result[1].name, "tag2")
    }
}
