package io.github.jezreal.store.repository

import io.github.jezreal.item.dto.AddStoreItemDto
import io.github.jezreal.tables.store.StoreInventory
import org.jetbrains.exposed.sql.insert
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
}