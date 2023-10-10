package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Location
import com.udomomo.liquornotes.domains.LocationRepository
import com.udomomo.liquornotes.ids.IdFactory
import com.udomomo.liquornotes.testhelper.ITBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest(properties = ["spring.config.location=classpath:/application-integration-test.yml"])
@ActiveProfiles("test")
@Transactional
class LocationRepositoryImplIT : ITBase() {
    @Autowired
    private lateinit var locationRepository: LocationRepository

    @Test
    fun `list location successfully`() {
        val id1 = IdFactory.generate()
        val location1 = Location.of(id1, "location1")
        val id2 = IdFactory.generate()
        val location2 = Location.of(id2, "location2")

        locationRepository.save(location1)
        locationRepository.save(location2)

        val result = locationRepository.listBy(listOf(id1, id2))

        Assertions.assertEquals(2, result.size)
        Assertions.assertEquals(result.first { it.id == id1 }.name, "location1")
        Assertions.assertEquals(result.first { it.id == id2 }.name, "location2")
    }

    @Test
    fun `get location successfully`() {
        val id = IdFactory.generate()
        val location = Location.of(id, "location")

        locationRepository.save(location)

        val result = locationRepository.findById(id)

        Assertions.assertNotNull(result)
        Assertions.assertEquals(result!!.id, id)
        Assertions.assertEquals(result.name, "location")
    }
}
