package io.github.jezreal.item

import io.github.jezreal.auth.isAccessToken
import io.github.jezreal.auth.isAdmin
import io.github.jezreal.auth.isStore
import io.github.jezreal.exception.AuthorizationException
import io.github.jezreal.item.dto.ItemCreatedDto
import io.github.jezreal.item.dto.ItemPriceDto
import io.github.jezreal.item.service.PriceService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.priceRoutes() {
    val priceService = PriceService
    authenticate {
        post("/price") {
            val principal = call.principal<JWTPrincipal>()

            if (!isAccessToken(principal!!)) {
                throw AuthorizationException("Invalid token type")
            }

            if (!isAdmin(principal)) {
                throw AuthorizationException("You are not allowed to access this resource")
            }

            val request = call.receive<ItemPriceDto>()
            call.respond(HttpStatusCode.Created, ItemCreatedDto(priceService.addItemInPriceList(request)))
        }

        get("/pricelist") {
            val principal = call.principal<JWTPrincipal>()

            if (!isAccessToken(principal!!)) {
                throw AuthorizationException("Invalid token type")
            }

            if (!isStore(principal)) {
                throw AuthorizationException("You are not allowed to access this resource")
            }

            call.respond(priceService.getPriceList())
        }

        get("/price/{itemId}") {
            val principal = call.principal<JWTPrincipal>()

            if (!isAccessToken(principal!!)) {
                throw AuthorizationException("Invalid token type")
            }

            if (!isStore(principal)) {
                throw AuthorizationException("You are not allowed to access this resource")
            }

            val itemId = call.parameters["itemId"]?.toLong() ?: throw BadRequestException("Invalid item id")

            call.respond(priceService.getPriceByItemId(itemId))
        }

        get("/price/category/{categoryId}") {
            val principal = call.principal<JWTPrincipal>()

            if (!isAccessToken(principal!!)) {
                throw AuthorizationException("Invalid token type")
            }

            if (!isStore(principal)) {
                throw AuthorizationException("You are not allowed to access this resource")
            }

            val categoryId = call.parameters["categoryId"]?.toLong() ?: throw BadRequestException("Invalid item id")

            call.respond(priceService.getPriceListByItemCategory(categoryId))
        }
    }
}