package io.github.jezreal.item.model

import io.github.jezreal.item.dto.ItemCategoryDto
import io.github.jezreal.item.dto.ItemCategoryWithIdDto

data class ItemCategoryModel(
    val itemCategoryId: Long,
    val itemCategory: String
)

fun List<ItemCategoryModel>.toItemCategoryWithIdDto() = map {
    ItemCategoryWithIdDto(
        it.itemCategoryId,
        it.itemCategory
    )
}

fun ItemCategoryModel.toItemCategoryDto() = ItemCategoryDto(
    this.itemCategory
)