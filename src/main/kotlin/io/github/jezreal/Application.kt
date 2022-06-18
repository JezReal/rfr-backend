package io.github.jezreal

import io.github.jezreal.configuration.Configuration
import io.github.jezreal.database.configureDatabase
import io.ktor.server.application.*
import io.github.jezreal.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    Configuration.setEnvironment(environment)
    configureDatabase()
    configureRouting()
    configureStatusPages()
    configureSecurity()
    configureHTTP()
    configureSerialization()
}
