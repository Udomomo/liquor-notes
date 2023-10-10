package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Tag
import com.udomomo.liquornotes.domains.TagRepository
import com.udomomo.liquornotes.ids.IdFactory
import com.udomomo.liquornotes.testhelper.ITBase
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(properties = ["spring.config.location=classpath:/application-integration-test.yml"])
@ActiveProfiles("test")
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
        assertEquals(result.first { it.id == id1 }.name, "tag1")
        assertEquals(result.first { it.id == id2 }.name, "tag2")
    }
}
