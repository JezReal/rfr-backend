package io.github.jezreal.item.service

import io.github.jezreal.exception.BadRequestException
import io.github.jezreal.exception.ResourceNotFoundException
import io.github.jezreal.item.dto.ItemPriceDto
import io.github.jezreal.item.model.ItemPriceModel
import io.github.jezreal.item.repository.ItemRepository
import io.github.jezreal.item.repository.PriceRepository

object PriceService {
    private val itemRepository = ItemRepository
    private val priceRepository = PriceRepository

    fun addItemInPriceList(itemPriceDto: ItemPriceDto): Long {
        val item = itemRepository.getItemByItemName(itemPriceDto.itemName)

        if (item != null) {
            throw BadRequestException("Item already exists")
        }

        return priceRepository.addItemToPriceList(itemPriceDto)
    }

    fun getPriceList(): List<ItemPriceModel> {
        return priceRepository.getPriceList()
    }

    fun getPriceByItemId(itemId: Long): ItemPriceModel {
        return priceRepository.getItemPriceByItemId(itemId) ?: throw ResourceNotFoundException("Item not found")
    }

    fun getPriceListByItemCategory(categoryId: Long): List<ItemPriceModel> {
        return priceRepository.getPriceListByCategoryId(categoryId)
    }
}