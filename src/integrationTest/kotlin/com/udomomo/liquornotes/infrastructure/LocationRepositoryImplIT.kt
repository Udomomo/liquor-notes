package com.udomomo.liquornotes.infrastructure

import com.udomomo.liquornotes.domains.Location
import com.udomomo.liquornotes.domains.LocationRepository
import com.udomomo.liquornotes.ids.IdFactory
import com.udomomo.liquornotes.testhelper.ITBase
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class LocationRepositoryImplIT: ITBase() {
    @Autowired
    private lateinit var locationRepository: LocationRepository

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
