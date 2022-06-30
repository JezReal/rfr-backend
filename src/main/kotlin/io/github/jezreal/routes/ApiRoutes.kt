package io.github.jezreal.routes

import io.github.jezreal.auth.authRoutes
import io.github.jezreal.item.itemRoutes
import io.github.jezreal.item.priceRoutes
import io.github.jezreal.response.Response
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.apiRoutes() {
    route(BASE_ROUTE) {
        get {
            call.respond(Response("RFR backend"))
        }
        authRoutes()
        priceRoutes()
        itemRoutes()
    }
}

private const val BASE_ROUTE = "/api"