package io.github.jezreal.item.dto

import com.google.gson.annotations.SerializedName

data class ItemCategoryDto(
    @SerializedName("item_category")
    val itemCategory: String
)
