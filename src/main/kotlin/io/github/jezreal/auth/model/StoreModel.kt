package io.github.jezreal.auth.model

data class StoreModel(
    val credentialId: Long,
    val username: String,
    val password: String,
    val storeId: Long,
    val storeName: String,
    val storeAddress: String
)
