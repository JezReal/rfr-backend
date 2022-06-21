package io.github.jezreal.item.dto

import com.google.gson.annotations.SerializedName

data class ItemPriceWithIdDto(
    @SerializedName("item_id")
    val itemId: Long,
    @SerializedName("item_category")
    val itemCategory: String,
    @SerializedName("item_name")
    val itemName: String,
    @SerializedName("price_per_unit_label")
    val pricePerUnitLabel: String,
    @SerializedName("price_per_unit")
    val pricePerUnit: Double,
//    @SerializedName("price_per_bag")
    val pricePerBag: Double
)
