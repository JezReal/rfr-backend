package io.github.jezreal.configuration

data class DatabaseConfiguration(
    val jdbcUrl: String,
    val user: String,
    val password: String,
    val maxPoolSize: Int
)
