package io.github.jezreal.item.repository

import io.github.jezreal.item.model.ItemCategoryModel
import io.github.jezreal.item.model.ItemModel
import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
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

    fun getItemByItemName(itemName: String): ItemModel? {
        return transaction {
            Items.join(ItemCategories, JoinType.INNER, additionalConstraint = {
                Items.itemCategoryId eq ItemCategories.itemCategoryId
            })
                .select { Items.itemName eq itemName }.firstOrNull()?.let {
                    ItemModel(
                        it[Items.itemId],
                        it[ItemCategories.itemCategoryId],
                        it[ItemCategories.itemCategory],
                        it[Items.itemName]
                    )
                }
        }
    }

    fun getItemCategories(): List<ItemCategoryModel> {
        return transaction {
            ItemCategories.selectAll().map {
                ItemCategoryModel(
                    it[ItemCategories.itemCategoryId],
                    it[ItemCategories.itemCategory]
                )
            }.distinctBy { it.itemCategoryId }
        }
    }

    fun getItemCategoryById(categoryId: Long): ItemCategoryModel? {
        return transaction {
            ItemCategories.select { ItemCategories.itemCategoryId eq categoryId }.firstOrNull()?.let {
                ItemCategoryModel(
                    it[ItemCategories.itemCategoryId],
                    it[ItemCategories.itemCategory]
                )
            }
        }
    }

    fun getItemByItemId(itemId: Long): ItemModel? {
        return transaction {
            Items.join(ItemCategories, JoinType.INNER, additionalConstraint = {
                Items.itemCategoryId eq ItemCategories.itemCategoryId
            }).select { Items.itemId eq itemId }.firstOrNull()?.let {
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