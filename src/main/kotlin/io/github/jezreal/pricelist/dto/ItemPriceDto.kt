package io.github.jezreal.pricelist.dto

data class ItemPriceDto(
    val itemCategory: String,
    val itemName: String,
    val pricePerUnitLabel: String,
    val pricePerUnit: Double,
    val pricePerBag: Double
)