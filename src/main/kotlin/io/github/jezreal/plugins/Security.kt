package io.github.jezreal.plugins

import io.github.jezreal.configuration.Configuration
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    val configuration = Configuration
    val securityConfiguration = configuration.getSecurityConfiguration()

    authentication {
        jwt {
            verifier(
                securityConfiguration.tokenVerifier()
            )
            validate { credential ->
                if (credential.payload.getClaim("email").asString() != "") {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
