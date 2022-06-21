package io.github.jezreal.tables.item

import io.github.jezreal.tables.price.Prices
import org.jetbrains.exposed.sql.Table

object Items : Table() {
    val itemId = long("item_id").autoIncrement()
    val itemName = varchar("store_item_name", 500)
    val priceId = long("price_id").references(Prices.priceId)
    val itemCategoryId = long("item_category_id").references(ItemCategories.itemCategoryId)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(itemId)
}