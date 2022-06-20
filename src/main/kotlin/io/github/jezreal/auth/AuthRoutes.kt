package io.github.jezreal.auth

import io.github.jezreal.auth.dto.LoginDto
import io.github.jezreal.auth.dto.RefreshTokenDto
import io.github.jezreal.auth.service.AuthService
import io.github.jezreal.exception.AuthorizationException
import io.github.jezreal.response.Response
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes() {
    val authService = AuthService

    post("/login") {
        val request = call.receive<LoginDto>()
        val tokenDto = authService.login(request)

        call.response.headers.append(HttpHeaders.Authorization, "Bearer ${tokenDto.accessToken}")
        call.respond(RefreshTokenDto(tokenDto.refreshToken))
    }

    post("/login/admin") {
        val request = call.receive<LoginDto>()
        val tokenDto = authService.loginAsAdmin(request)

        call.response.headers.append(HttpHeaders.Authorization, "Bearer ${tokenDto.accessToken}")
        call.respond(RefreshTokenDto(tokenDto.refreshToken))
    }

    post("/refresh") {
        val request = call.receive<RefreshTokenDto>()
        val accessToken = authService.refreshAccessToken(request)

        call.response.headers.append(HttpHeaders.Authorization, "Bearer $accessToken")

        call.respond(HttpStatusCode.OK)
    }

    authenticate {
        get("/protected") {
            val principal = call.principal<JWTPrincipal>()

            if (!isAccessToken(principal!!)) {
                throw AuthorizationException("Invalid token type")
            }

            call.respond(Response("Hello authenticated user"))
        }

        get("/protected/admin") {
            val principal = call.principal<JWTPrincipal>()

            if (!isAccessToken(principal!!)) {
                throw AuthorizationException("Invalid token type")
            }

            if (!isAdmin(principal)) {
                throw AuthorizationException("You are not allowed to access this resource")
            }

            call.respond(Response("Hello authenticated admin"))
        }
    }
}