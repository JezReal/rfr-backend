package io.github.jezreal.auth.model

data class TokenModel(
    val accessToken: String,
    val refreshToken: String
)
