package io.github.jezreal.plugins

import io.github.jezreal.exception.AuthenticationException
import io.github.jezreal.exception.AuthorizationException
import io.github.jezreal.exception.BadRequestException
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

        exception<AuthenticationException> { call, exception ->
            call.respond(HttpStatusCode.Unauthorized, Response(exception.message))
        }

        exception<ResourceNotFoundException> { call, exception ->
            call.respond(HttpStatusCode.NotFound, Response(exception.message))
        }

        exception<AuthorizationException> { call, exception ->
            call.respond(HttpStatusCode.Forbidden, Response(exception.message))
        }

        status(HttpStatusCode.Unauthorized) { statusCode ->
            call.respond(statusCode, Response("Invalid token"))
        }

        exception<NumberFormatException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, Response("Invalid request body"))
        }

        exception<BadRequestException> { call, exception ->
            call.respond(HttpStatusCode.BadRequest, Response(exception.message))
        }

        exception<NullPointerException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest, Response("Invalid request body"))
        }

        status(HttpStatusCode.InternalServerError) { statusCode ->
            call.respond(statusCode, Response("Something went wrong with the server"))
        }
    }
}