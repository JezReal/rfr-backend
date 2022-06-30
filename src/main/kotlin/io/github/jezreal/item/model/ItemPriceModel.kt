package io.github.jezreal.item.model

import io.github.jezreal.exception.ResourceNotFoundException
import io.github.jezreal.item.dto.ItemPriceByCategoryDto
import io.github.jezreal.item.dto.ItemPriceByCategoryWithIdDto
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

fun List<ItemPriceModel>.toItemPriceByCategoryDto(): ItemPriceByCategoryDto {
    val category = this.getOrNull(0)?.itemCategory ?: throw ResourceNotFoundException("Category not found")

    val items = this.map {
        ItemPriceWithIdDto(
            it.itemId,
            it.itemName,
            it.pricePerUnitLabel,
            it.pricePerUnit,
            it.pricePerBag
        )
    }

    return ItemPriceByCategoryDto(
        category,
        items
    )
}

fun List<ItemPriceModel>.toItemPriceByCategoryWithIdDto(): List<ItemPriceByCategoryWithIdDto> {
    val categories = this.distinctBy { it.itemCategory }
    val priceListByCategory = mutableListOf<ItemPriceByCategoryWithIdDto>()

    for (category in categories) {
        val pricesInCategory = this.filter {
            it.itemCategory == category.itemCategory
        }.toItemPriceWithIdDto().sortedBy { it.itemId }

        priceListByCategory.add(
            ItemPriceByCategoryWithIdDto(
                category.itemCategoryId,
                category.itemCategory,
                pricesInCategory
            )
        )
    }

    return priceListByCategory
}