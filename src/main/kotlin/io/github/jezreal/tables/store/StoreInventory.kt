package io.github.jezreal.tables.store

import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import org.jetbrains.exposed.sql.Table

object StoreInventory : Table() {
    val storeItemId = long("store_inventory_id").autoIncrement()
    val storeItemAmount = long("item_amount")
    val itemId = long("item_id").references(Items.itemId)
    val storeId = long("store_id").references(Stores.storeId)
    val itemCategoryId = long("item_category_id").references(ItemCategories.itemCategoryId)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(storeItemId)
}