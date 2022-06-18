package io.github.jezreal.auth.model

data class UserCredentialModel(
    val username: String,
    val password: String,
    val role: String,
    val credentialId: Long
)
