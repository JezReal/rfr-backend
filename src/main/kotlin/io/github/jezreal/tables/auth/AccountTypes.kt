package io.github.jezreal.tables.auth

import org.jetbrains.exposed.sql.Table

object AccountTypes : Table() {
    val accountTypeId = long("account_type_id").autoIncrement()
    val accountType = varchar("account_type", 100)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(accountTypeId)
}