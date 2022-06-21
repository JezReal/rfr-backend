package io.github.jezreal.tables.store

import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import org.jetbrains.exposed.sql.Table

object StoreItems : Table() {
    val storeItemId = long("store_item_id").autoIncrement()
    val storeItemAmount = long("store_item_amount")
    val itemId = long("item_id").references(Items.itemId)
    val storeId = long("store_id").references(Stores.storeId)
    val itemCategoryId = long("store_item_category_id").references(ItemCategories.itemCategoryId)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(storeItemId)
}