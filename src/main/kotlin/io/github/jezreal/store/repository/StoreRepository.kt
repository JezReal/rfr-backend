package io.github.jezreal.store.repository

import io.github.jezreal.item.dto.AddStoreItemDto
import io.github.jezreal.store.model.ItemInventoryModel
import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import io.github.jezreal.tables.store.StoreInventory
import io.github.jezreal.tables.store.Stores
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object StoreRepository {
    fun addItemToStoreInventory(addStoreItemDto: AddStoreItemDto, storeId: Long, itemCategoryId: Long): Long {
        return transaction {
            StoreInventory.insert {
                it[itemId] = addStoreItemDto.itemId
                it[StoreInventory.storeId] = storeId
                it[StoreInventory.itemCategoryId] = itemCategoryId
                it[itemAmountKg] = addStoreItemDto.itemAmountKg
                it[weightPerBag] = addStoreItemDto.weightPerBag
            } get StoreInventory.storeInventoryId
        }
    }

    fun getStoreInventory(storeId: Long): List<ItemInventoryModel> {
        return transaction {
            StoreInventory
                .join(Stores, JoinType.INNER, additionalConstraint = {
                StoreInventory.storeId eq Stores.storeId
            })
                .join(Items, JoinType.INNER, additionalConstraint = {
                    StoreInventory.itemId eq Items.itemId
                })
                .join(ItemCategories, JoinType.INNER, additionalConstraint = {
                    Items.itemCategoryId eq ItemCategories.itemCategoryId
                })
                .select { Stores.storeId eq storeId }.map {
                    ItemInventoryModel(
                        it[Items.itemId],
                        it[ItemCategories.itemCategoryId],
                        it[ItemCategories.itemCategory],
                        it[Items.itemName],
                        it[StoreInventory.itemAmountKg],
                        it[StoreInventory.weightPerBag]
                    )
                }
        }
    }
}