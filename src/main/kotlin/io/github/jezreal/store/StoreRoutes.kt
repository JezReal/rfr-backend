package io.github.jezreal.store

import io.github.jezreal.auth.isAccessToken
import io.github.jezreal.auth.isStore
import io.github.jezreal.exception.AuthorizationException
import io.github.jezreal.exception.BadRequestException
import io.github.jezreal.item.dto.AddStoreItemDto
import io.github.jezreal.item.dto.InventoryItemCreatedDto
import io.github.jezreal.store.service.StoreService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.storeRoutes() {
    val storeService = StoreService
    authenticate {
        route("/store") {
            post("/item") {
                val principal = call.principal<JWTPrincipal>()

                if (!isAccessToken(principal!!)) {
                    throw AuthorizationException("Invalid token type")
                }

                if (!isStore(principal)) {
                    throw AuthorizationException("You are not allowed to access this resource")
                }

                val requestBody = call.receive<AddStoreItemDto>()

                if (requestBody.itemAmountKg == 0.0 || requestBody.weightPerBag == 0.0
                    || requestBody.itemAmountKg < 0 || requestBody.weightPerBag < 0
                ) {
                    throw BadRequestException("Invalid weight")
                }

                val username = principal.payload.getClaim("username").asString()

                call.respond(
                    HttpStatusCode.Created,
                    InventoryItemCreatedDto(storeService.addItemToStoreInventory(requestBody, username))
                )
            }
        }
    }
}