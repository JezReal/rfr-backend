package io.github.jezreal.item

import io.github.jezreal.auth.isAccessToken
import io.github.jezreal.auth.isStore
import io.github.jezreal.exception.AuthorizationException
import io.github.jezreal.item.model.toItemCategoryDto
import io.github.jezreal.item.model.toItemCategoryWithIdDto
import io.github.jezreal.item.service.ItemService
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.itemRoutes() {
    val itemService = ItemService

    authenticate {
        get("/items/categories") {
            val principal = call.principal<JWTPrincipal>()

            if (!isAccessToken(principal!!)) {
                throw AuthorizationException("Invalid token type")
            }

            if (!isStore(principal)) {
                throw AuthorizationException("You are not allowed to access this resource")
            }

            call.respond(itemService.getItemCategories().toItemCategoryWithIdDto())
        }

        get("/items/categories/{categoryId}") {
            val principal = call.principal<JWTPrincipal>()

            if (!isAccessToken(principal!!)) {
                throw AuthorizationException("Invalid token type")
            }

            if (!isStore(principal)) {
                throw AuthorizationException("You are not allowed to access this resource")
            }

            val categoryId = call.parameters["categoryId"]?.toLong() ?: throw BadRequestException("Invalid category id")

            call.respond(itemService.getItemCategoryById(categoryId).toItemCategoryDto())
        }
    }
}