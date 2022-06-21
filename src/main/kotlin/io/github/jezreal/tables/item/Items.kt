package io.github.jezreal.tables.item

import org.jetbrains.exposed.sql.Table

object Items : Table() {
    val itemId = long("item_id").autoIncrement()
    val itemName = varchar("item_name", 500)
    val itemCategoryId = long("item_category_id").references(ItemCategories.itemCategoryId)
    val pricePerUnitLabel = varchar("price_per_unit_label", 500)
    val pricePerUnit = double("price_per_unit")
    val pricePerBag = double("price_per_bag")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(itemId)
}