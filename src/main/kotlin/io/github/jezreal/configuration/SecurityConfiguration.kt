package io.github.jezreal.configuration

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import java.util.*

class SecurityConfiguration(
    private val secret: String,
    private val issuer: String,
    private val audience: String
) {

    fun makeAccessToken(username: String, role: String): String = JWT.create()
        .withAudience(audience)
        .withIssuer(issuer)
        .withClaim("username", username)
        .withClaim("role", role)
        .withClaim("type", "access")
        .withExpiresAt(Date(System.currentTimeMillis() + 300000))
        .sign(Algorithm.HMAC256(secret))

    fun tokenVerifier(): JWTVerifier = JWT
        .require(Algorithm.HMAC256(secret))
        .withAudience(audience)
        .withIssuer(issuer)
        .build()

    fun makeRefreshToken(id: Long): String {
        val day = (1000 * 60 * 60 * 24).toLong() // 24 hours in milliseconds
        val week = day * 7

        return JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("credentialId", id)
            .withClaim("type", "refresh")
            .withExpiresAt(Date(System.currentTimeMillis() + week))
            .sign(Algorithm.HMAC256(secret))
    }

    fun validateRefreshToken(token: String): DecodedJWT? {
        val verifiedToken = tokenVerifier().verify(token)
        if (verifiedToken.getClaim("type").asString() == "refresh") {
            return verifiedToken
        }

        return null
    }

    fun isTokenExpired(token: String): Boolean {
        return try {
            tokenVerifier().verify(token)
            false
        } catch (exception: TokenExpiredException) {
            true
        }
    }
}
