package com.udomomo.liquornotes.testhelper

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import javax.sql.DataSource

@Configuration
class TestDataSourceConfig {
    @Bean
    @Profile("test")
    @ConfigurationProperties(prefix = "spring.datasource")
    fun datasource(): DataSource = DataSourceBuilder.create().build()
}
