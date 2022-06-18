package io.github.jezreal.auth

import io.ktor.server.auth.jwt.*

fun isAccessToken(principal: JWTPrincipal): Boolean {
    val type = principal.payload.getClaim("type").asString()

    return type == TokenTypes.ACCESS
}

fun isRefreshToken(principal: JWTPrincipal): Boolean {
    val type = principal.payload.getClaim("type").asString()

    return type == TokenTypes.REFRESH
}

fun isAdmin(principal: JWTPrincipal): Boolean {
    val role = principal.payload.getClaim("role").asString()

    return role == RoleUtil.ADMIN
}