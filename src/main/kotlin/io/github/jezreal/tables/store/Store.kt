package io.github.jezreal.tables.store

import io.github.jezreal.tables.auth.Credentials
import org.jetbrains.exposed.sql.Table

object Store : Table() {
    val storeId = long("store_id").autoIncrement()
    val storeName = text("store_name")
    val storeAddress = text("store_address")
    val credentialId = long("credential_id").references(Credentials.credentialId)
    
    override val primaryKey: PrimaryKey
        get() = PrimaryKey(storeId)
}