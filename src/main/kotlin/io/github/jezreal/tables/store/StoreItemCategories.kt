package io.github.jezreal.tables.store

import org.jetbrains.exposed.sql.Table

object StoreItemCategories : Table() {
    val storeItemCategoryId = long("store_item_category_id").autoIncrement()
    val storeItemCategory = varchar("store_item_category", 500)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(storeItemCategoryId)
}