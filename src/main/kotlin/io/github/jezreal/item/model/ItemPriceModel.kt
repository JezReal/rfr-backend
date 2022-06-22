package io.github.jezreal.item.model

import io.github.jezreal.item.dto.ItemPriceDto
import io.github.jezreal.item.dto.ItemPriceWithIdDto

data class ItemPriceModel(
    val itemId: Long,
    val itemCategoryId: Long,
    val itemCategory: String,
    val itemName: String,
    val pricePerUnitLabel: String,
    val pricePerUnit: Double,
    val pricePerBag: Double
)

fun List<ItemPriceModel>.toItemPriceWithIdDto() = map {
    ItemPriceWithIdDto(
        it.itemId,
        it.itemName,
        it.pricePerUnitLabel,
        it.pricePerUnit,
        it.pricePerBag
    )
}

fun ItemPriceModel.toItemPriceDto() = ItemPriceDto(
    this.itemCategory,
    this.itemName,
    this.pricePerUnitLabel,
    this.pricePerUnit,
    this.pricePerBag
)
