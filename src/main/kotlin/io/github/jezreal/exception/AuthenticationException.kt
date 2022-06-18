package io.github.jezreal.exception

class AuthenticationException(private val errorMessage: String) : Exception() {
    override val message: String
        get() = errorMessage
}