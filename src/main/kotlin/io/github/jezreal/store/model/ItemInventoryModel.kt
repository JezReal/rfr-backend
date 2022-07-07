package io.github.jezreal.store.model

import io.github.jezreal.store.dto.ItemInventoryByCategoryWithIdDto
import io.github.jezreal.store.dto.ItemInventoryWithIdDto

data class ItemInventoryModel(
    val itemId: Long,
    val itemCategoryId: Long,
    val itemCategory: String,
    val itemName: String,
    val itemAmountKg: Double,
    val weightPerBag: Double
)

fun List<ItemInventoryModel>.toItemInventoryByCategoryWithIdDto(): List<ItemInventoryByCategoryWithIdDto> {
    val categories = this.distinctBy { it.itemCategoryId }
    val inventoryByCategory = mutableListOf<ItemInventoryByCategoryWithIdDto>()

    for (item in categories) {
        val itemsInCategory = this.filter {
            it.itemCategoryId == item.itemCategoryId
        }.toItemInventoryWithIdDto().sortedBy { it.itemId }

        inventoryByCategory.add(
            ItemInventoryByCategoryWithIdDto(
                item.itemCategoryId,
                item.itemCategory,
                itemsInCategory
            )
        )
    }

    return inventoryByCategory
}

fun List<ItemInventoryModel>.toItemInventoryWithIdDto() = map {
    ItemInventoryWithIdDto(
        it.itemId,
        it.itemName,
        it.itemAmountKg,
        it.weightPerBag
    )
}
