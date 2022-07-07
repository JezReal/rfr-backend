package io.github.jezreal.store.dto

data class ItemInventoryWithIdDto(
    val itemId: Long,
    val itemName: String,
    val itemAmountKg: Double,
    val weightPerBag: Double,
    val itemAmountBags: Double = itemAmountKg / weightPerBag
)
