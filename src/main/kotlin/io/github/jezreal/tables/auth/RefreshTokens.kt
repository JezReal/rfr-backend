package io.github.jezreal.tables.auth

import org.jetbrains.exposed.sql.Table

object RefreshTokens : Table() {
    val refreshTokenId = long("refresh_token_id").autoIncrement()
    val refreshToken = text("refresh_token")
    val credentialId = long("credential_id").references(Credentials.credentialId)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(refreshTokenId)
}