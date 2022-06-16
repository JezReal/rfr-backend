package io.github.jezreal.plugins

import io.github.jezreal.routes.apiRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
//    install(StatusPages) {
//        exception<AuthenticationException> { call, cause ->
//            call.respond(HttpStatusCode.Unauthorized)
//        }
//        exception<AuthorizationException> { call, cause ->
//            call.respond(HttpStatusCode.Forbidden)
//        }
//
//    }
    routing {
        apiRoutes()
    }
}
