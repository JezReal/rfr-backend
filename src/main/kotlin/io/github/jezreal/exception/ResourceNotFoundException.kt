package io.github.jezreal.exception

class ResourceNotFoundException(private val errorMessage: String) : Exception() {
    override val message: String
        get() = errorMessage
}

