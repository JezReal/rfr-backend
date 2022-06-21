package io.github.jezreal.item.service

import io.github.jezreal.exception.BadRequestException
import io.github.jezreal.item.dto.ItemPriceDto
import io.github.jezreal.item.repository.ItemRepository
import io.github.jezreal.item.repository.PriceRepository

object PriceService {
    private val itemRepository = ItemRepository
    private val priceRepository = PriceRepository

    fun addItemInPriceList(itemPriceDto: ItemPriceDto): Long {
        val item  = itemRepository.getItemByItemName(itemPriceDto.itemName)

        if (item != null) {
            throw BadRequestException("Item already exists")
        }

        return priceRepository.addItemToPriceList(itemPriceDto)
    }
}