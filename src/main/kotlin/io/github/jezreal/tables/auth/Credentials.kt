package io.github.jezreal.tables.auth

import org.jetbrains.exposed.sql.Table

object Credentials : Table() {
    val credentialId = long("credential_id").autoIncrement()
    val emailAddress = varchar("email_address", 100)
    val password = text("password")
    val accountTypeId = long("account_type_id").references(AccountTypes.accountTypeId)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(credentialId)
}