package com.udomomo.liquornotes.testhelper

import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach

abstract class ITBase {
    @AfterEach
    fun tearDown() {
        transaction {
            db.dialect.allTablesNames().forEach {
                TransactionManager.current().exec("TRUNCATE TABLE ${it.split(".")[1]}")
            }
        }
    }
}
