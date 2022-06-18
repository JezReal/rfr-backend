package io.github.jezreal.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.jezreal.configuration.Configuration
import io.github.jezreal.tables.auth.AccountTypes
import io.github.jezreal.tables.auth.Credentials
import io.github.jezreal.tables.price.Prices
import io.github.jezreal.tables.store.Store
import io.github.jezreal.tables.store.StoreItemCategories
import io.github.jezreal.tables.store.StoreItems
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase() {
    val hikariConfig = HikariConfig()
    val configuration = Configuration
    val databaseConfig = configuration.getDatabaseConfiguration()

    hikariConfig.jdbcUrl = databaseConfig.jdbcUrl
    hikariConfig.username = databaseConfig.user
    hikariConfig.password = databaseConfig.password
    hikariConfig.maximumPoolSize = databaseConfig.maxPoolSize

    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)

//    createTables()
}

private fun createTables() {
    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(
            Credentials,
            AccountTypes,
            Prices,
            Store,
            StoreItemCategories,
            StoreItems
        )
    }
}