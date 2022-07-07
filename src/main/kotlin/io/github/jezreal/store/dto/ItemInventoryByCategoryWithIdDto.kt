package io.github.jezreal.store.dto

import com.google.gson.annotations.SerializedName

data class ItemInventoryByCategoryWithIdDto(
    @SerializedName("category_id")
    val categoryId: Long,
    val category: String,
    val items: List<ItemInventoryWithIdDto>
)
