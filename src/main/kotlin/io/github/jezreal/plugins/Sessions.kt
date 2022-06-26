package io.github.jezreal.plugins

import io.github.jezreal.auth.dto.RefreshToken
import io.ktor.server.application.*
import io.ktor.server.sessions.*

fun Application.configureSession() {
    val day = (60 * 60 * 24).toLong() // 24 hours in seconds
    val week = day * 7

    install(Sessions) {
        cookie<RefreshToken>("refresh-token") {
            cookie.httpOnly = true
            cookie.maxAgeInSeconds = week
        }
    }
}