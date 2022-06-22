package io.github.jezreal.item.service

import io.github.jezreal.exception.BadRequestException
import io.github.jezreal.item.dto.ItemPriceByCategoryDto
import io.github.jezreal.item.dto.ItemPriceDto
import io.github.jezreal.item.model.toItemPriceWithIdDto
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

    fun getPriceList(): List<ItemPriceByCategoryDto> {
        val priceList = priceRepository.getPriceList()
        val categories = priceList.distinctBy { it.itemCategory }
        val priceListByCategory = mutableListOf<ItemPriceByCategoryDto>()

        for (category in categories) {
            val pricesInCategory = priceList.filter {
                it.itemCategory == category.itemCategory
            }.toItemPriceWithIdDto()

            priceListByCategory.add(
                ItemPriceByCategoryDto(
                    category.itemCategory,
                    pricesInCategory
                )
            )
        }

        return priceListByCategory
    }
}