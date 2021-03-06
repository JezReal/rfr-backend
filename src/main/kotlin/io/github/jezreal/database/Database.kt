package io.github.jezreal.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.jezreal.configuration.Configuration
import io.github.jezreal.tables.auth.AccountTypes
import io.github.jezreal.tables.auth.Credentials
import io.github.jezreal.tables.auth.RefreshTokens
import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import io.github.jezreal.tables.store.StoreInventory
import io.github.jezreal.tables.store.Stores
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

//    dropTables()
//    createTables()
//    prepopulateTables()
}

private fun createTables() {
    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(
            RefreshTokens,
            ItemCategories,
            Items,
            Credentials,
            AccountTypes,
            Stores,
            StoreInventory
        )
    }
}

private fun dropTables() {
    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.drop(
            RefreshTokens,
            ItemCategories,
            Items,
            Stores,
            AccountTypes,
            StoreInventory,
            Credentials,
        )
    }
}

fun prepopulateTables() {
    insertAccountTypes()
    insertTestAccount()
    insertAdminAccount()
    insertInitialPriceList()
}