package io.github.jezreal.tables.item

import org.jetbrains.exposed.sql.Table

object ItemCategories : Table() {
    val itemCategoryId = long("item_category_id").autoIncrement()
    val itemCategory = varchar("item_category", 500)

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(itemCategoryId)
}