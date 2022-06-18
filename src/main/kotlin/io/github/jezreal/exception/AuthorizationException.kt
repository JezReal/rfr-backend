package io.github.jezreal.exception

class AuthorizationException(private val errorMessage: String) : Exception() {
    override val message: String
        get() = errorMessage
}