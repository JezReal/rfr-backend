package io.github.jezreal.item.dto

import com.google.gson.annotations.SerializedName

data class ItemPriceByCategoryDto(
    @SerializedName("category_id")
    val categoryId: Long,
    val category: String,
    val items: List<ItemPriceWithIdDto>
)
