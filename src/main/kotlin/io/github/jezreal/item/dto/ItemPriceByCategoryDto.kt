package io.github.jezreal.item.dto

data class ItemPriceByCategoryDto(
    val category: String,
    val items: List<ItemPriceWithIdDto>
)
