package io.github.jezreal.item.repository

import io.github.jezreal.item.model.ItemModel
import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object ItemRepository {
    fun getItemByItemCategoryName(itemCategoryName: String): ItemModel? {
        return transaction {
            Items.join(ItemCategories, JoinType.INNER, additionalConstraint = {
                Items.itemCategoryId eq ItemCategories.itemCategoryId
            })
                .select { ItemCategories.itemCategory eq itemCategoryName }.firstOrNull()?.let {
                    ItemModel(
                        it[Items.itemId],
                        it[ItemCategories.itemCategoryId],
                        it[ItemCategories.itemCategory],
                        it[Items.itemName]
                    )
                }
        }
    }
}