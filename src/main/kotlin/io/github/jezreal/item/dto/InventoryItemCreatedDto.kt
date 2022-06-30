package io.github.jezreal.item.dto

import com.google.gson.annotations.SerializedName

data class InventoryItemCreatedDto(
    @SerializedName("store_inventory_id")
    val storeInventoryId: Long
)