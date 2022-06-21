package io.github.jezreal.exception

class BadRequestException(private val errorMessage: String) : Exception() {
    override val message: String
        get() = errorMessage
}