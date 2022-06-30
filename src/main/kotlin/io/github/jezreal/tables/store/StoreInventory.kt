package io.github.jezreal.tables.store

import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import org.jetbrains.exposed.sql.Table

object StoreInventory : Table() {
    val storeInventoryId = long("store_inventory_id").autoIncrement()
    val itemAmountKg = double("item_amount_in_kg")
    val weightPerBag = double("weight_per_bag")
    val itemId = long("item_id").references(Items.itemId)
    val storeId = long("store_id").references(Stores.storeId)
    val itemCategoryId = long("item_category_id").references(ItemCategories.itemCategoryId)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(storeInventoryId)
}