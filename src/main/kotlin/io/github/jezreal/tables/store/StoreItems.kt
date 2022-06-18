package io.github.jezreal.tables.store

import io.github.jezreal.tables.price.Prices
import org.jetbrains.exposed.sql.Table

object StoreItems : Table() {
    val storeItemId = long("store_item_id").autoIncrement()
    val storeItemName = varchar("store_item_name", 500)
    val storeItemAmount = long("store_item_amount")
    val priceId = long("price_id").references(Prices.priceId)
    val storeId = long("store_id").references(Stores.storeId)
    val storeItemCategoryId = long("store_item_category_id").references(StoreItemCategories.storeItemCategoryId)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(storeItemId)
}