package com.udomomo.liquornotes.configs

import org.jetbrains.exposed.spring.SpringTransactionManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.annotation.TransactionManagementConfigurer
import javax.sql.DataSource

/**
 * Exposed利用下でSpring Bootの@Transactionalを有効にするための設定。
 * Exposedで必要なDatabase.connect()も、SpringTransactionManager内で行っている。
 */
@Configuration
@EnableTransactionManagement
class DataTransactionConfig(private val datasource: DataSource) : TransactionManagementConfigurer {

    @Bean
    override fun annotationDrivenTransactionManager() = SpringTransactionManager(datasource)
}
