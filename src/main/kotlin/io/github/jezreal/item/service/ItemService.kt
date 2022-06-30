package io.github.jezreal.item.service

import io.github.jezreal.exception.ResourceNotFoundException
import io.github.jezreal.item.model.ItemCategoryModel
import io.github.jezreal.item.repository.ItemRepository

object ItemService {
    private val itemRepository = ItemRepository

    fun getItemCategories(): List<ItemCategoryModel> {
        return itemRepository.getItemCategories()
    }

    fun getItemCategoryById(categoryId: Long): ItemCategoryModel {
        return itemRepository.getItemCategoryById(categoryId) ?: throw ResourceNotFoundException("Category not found")
    }
}