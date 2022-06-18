package io.github.jezreal.database

import at.favre.lib.crypto.bcrypt.BCrypt
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.github.jezreal.configuration.Configuration
import io.github.jezreal.constants.AccountTypeConstants
import io.github.jezreal.tables.auth.AccountTypes
import io.github.jezreal.tables.auth.Credentials
import io.github.jezreal.tables.price.Prices
import io.github.jezreal.tables.store.Stores
import io.github.jezreal.tables.store.StoreItemCategories
import io.github.jezreal.tables.store.StoreItems
import org.jetbrains.exposed.sql.*
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

    dropTables()
    createTables()
    prepopulateTables()
}

private fun createTables() {
    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(
            Credentials,
            AccountTypes,
            Prices,
            Stores,
            StoreItemCategories,
            StoreItems
        )
    }
}

private fun dropTables() {
    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.drop(
            Stores,
            AccountTypes,
            Prices,
            StoreItemCategories,
            StoreItems,
            Credentials,
        )
    }
}

fun prepopulateTables() {
    insertAccountTypes()
    insertTestAccount()
    insertAdminAccount()
}

fun insertAccountTypes() {
    val accountTypes = listOf("store", "admin")
    transaction {
        AccountTypes.batchInsert(accountTypes, shouldReturnGeneratedValues = false)
        { accountTypeName ->
            this[AccountTypes.accountType] = accountTypeName
        }
    }

}

fun insertTestAccount() {
    val storeAccount = Configuration.getStoreAccountConfiguration()
    val hashedPassword = BCrypt.withDefaults().hashToString(12, storeAccount.password.toCharArray())
    println(storeAccount.username)
    val credentialId = transaction {
        Credentials.insert {
            it[username] = storeAccount.username
            it[password] = hashedPassword
            it[accountTypeId] = AccountTypeConstants.STORE.toLong()
        } get Credentials.credentialId
    }

    transaction {
        Stores.insert {
            it[storeName] = storeAccount.storeName
            it[storeAddress] = storeAccount.storeAddress
            it[Stores.credentialId] = credentialId
        }
    }
}

fun insertAdminAccount() {
    val adminAccount = Configuration.getAdminAccountConfiguration()
    val hashedPassword = BCrypt.withDefaults().hashToString(12, adminAccount.password.toCharArray())

    transaction {
        Credentials.insert {
            it[username] = adminAccount.username
            it[password] = hashedPassword
            it[accountTypeId] = AccountTypeConstants.ADMIN.toLong()
        } get Credentials.credentialId
    }
}