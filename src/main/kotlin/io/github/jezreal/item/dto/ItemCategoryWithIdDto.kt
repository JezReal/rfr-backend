package io.github.jezreal.item.dto

import com.google.gson.annotations.SerializedName

data class ItemCategoryWithIdDto(
    @SerializedName("item_category_id")
    val itemCategoryId: Long,
    @SerializedName("item_category")
    val itemCategory: String
)
