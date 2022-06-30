package io.github.jezreal.item.dto

import com.google.gson.annotations.SerializedName

data class AddStoreItemDto(
    @SerializedName("item_id")
    val itemId: Long,
    @SerializedName("item_amount_kg")
    val itemAmountKg: Double,
    @SerializedName("weight_per_bag")
    val weightPerBag: Double
)
