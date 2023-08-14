package com.udomomo.liquornotes.configs

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun datasource(): DataSource = DataSourceBuilder.create().build()
}
