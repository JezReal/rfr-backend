package io.github.jezreal.item.dto

import com.google.gson.annotations.SerializedName

data class ItemCreatedDto(
    @SerializedName("item_id")
    val itemId: Long
)
