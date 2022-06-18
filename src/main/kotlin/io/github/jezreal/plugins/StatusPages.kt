package io.github.jezreal.plugins

import io.github.jezreal.exception.ResourceNotFoundException
import io.github.jezreal.response.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ResourceNotFoundException> { call, exception ->
            call.respond(HttpStatusCode.NotFound, Response(exception.message))
        }
    }
}