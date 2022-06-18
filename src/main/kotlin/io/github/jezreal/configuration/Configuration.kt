package io.github.jezreal.configuration

import io.ktor.server.application.*

object Configuration {
    private lateinit var environment: ApplicationEnvironment

    fun setEnvironment(environment: ApplicationEnvironment) {
        this.environment = environment
    }

    fun getDatabaseConfiguration(): DatabaseConfiguration = DatabaseConfiguration(
        environment.config.property("ktor.security.database.jdbcUrl").getString(),
        environment.config.property("ktor.security.database.user").getString(),
        environment.config.property("ktor.security.database.password").getString(),
        environment.config.property("ktor.security.database.maxPoolSize").getString().toInt()
    )
}