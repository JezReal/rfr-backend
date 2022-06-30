package io.github.jezreal.store.service

import io.github.jezreal.auth.service.AuthService
import io.github.jezreal.item.dto.AddStoreItemDto
import io.github.jezreal.item.service.ItemService
import io.github.jezreal.store.repository.StoreRepository

object StoreService {
    private val authService = AuthService
    private val itemService = ItemService
    private val storeRepository = StoreRepository

    fun addItemToStoreInventory(addStoreItemDto: AddStoreItemDto, username: String): Long {
        val storeId = authService.getStoreInfoByUsername(username).storeId
        val itemCategoryId = itemService.getItemByItemId(addStoreItemDto.itemId).itemCategoryId

        return storeRepository.addItemToStoreInventory(addStoreItemDto, storeId, itemCategoryId)
    }
}