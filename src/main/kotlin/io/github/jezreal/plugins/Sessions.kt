package io.github.jezreal.plugins

import io.github.jezreal.auth.dto.RefreshToken
import io.ktor.server.application.*
import io.ktor.server.sessions.*
import io.ktor.util.*

fun Application.configureSession() {
    val day = (60 * 60 * 24).toLong() // 24 hours in seconds
    val week = day * 7

    val secretEncryptKey = hex(environment.config.property("ktor.security.secureKeys.encryptKey").getString())
    val secretSignKey = hex(environment.config.property("ktor.security.secureKeys.signKey").getString())

    install(Sessions) {
        cookie<RefreshToken>("refresh-token") {
            cookie.httpOnly = true
            cookie.maxAgeInSeconds = week
            transform(SessionTransportTransformerEncrypt(secretEncryptKey, secretSignKey))
        }
    }
}