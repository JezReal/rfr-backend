package io.github.jezreal.configuration

import io.ktor.server.application.*

object Configuration {
    private lateinit var environment: ApplicationEnvironment

    fun setEnvironment(environment: ApplicationEnvironment) {
        this.environment = environment
    }

    fun getDatabaseConfiguration() = DatabaseConfiguration(
        environment.config.property("ktor.security.database.jdbcUrl").getString(),
        environment.config.property("ktor.security.database.user").getString(),
        environment.config.property("ktor.security.database.password").getString(),
        environment.config.property("ktor.security.database.maxPoolSize").getString().toInt()
    )

    fun getSecurityConfiguration() = SecurityConfiguration(
        environment.config.property("ktor.security.jwt.secret").getString(),
        environment.config.property("ktor.security.jwt.issuer").getString(),
        environment.config.property("ktor.security.jwt.audience").getString()
    )

    fun getStoreAccountConfiguration() = StoreAccountConfiguration(
        environment.config.property("ktor.security.storeAccount.username").getString(),
        environment.config.property("ktor.security.storeAccount.password").getString(),
        environment.config.property("ktor.security.storeAccount.storeName").getString(),
        environment.config.property("ktor.security.storeAccount.storeAddress").getString(),
    )

    fun getAdminAccountConfiguration() = AdminAccountConfiguration(
        environment.config.property("ktor.security.adminAccount.username").getString(),
        environment.config.property("ktor.security.adminAccount.password").getString()
    )
}