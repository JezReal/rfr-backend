package io.github.jezreal.item.service

import io.github.jezreal.exception.BadRequestException
import io.github.jezreal.exception.ResourceNotFoundException
import io.github.jezreal.item.dto.ItemPriceByCategoryDto
import io.github.jezreal.item.dto.ItemPriceByCategoryWithIdDto
import io.github.jezreal.item.dto.ItemPriceDto
import io.github.jezreal.item.model.toItemPriceByCategoryDto
import io.github.jezreal.item.model.toItemPriceDto
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

    fun getPriceList(): List<ItemPriceByCategoryWithIdDto> {
        val priceList = priceRepository.getPriceList()
        val categories = priceList.distinctBy { it.itemCategory }
        val priceListByCategory = mutableListOf<ItemPriceByCategoryWithIdDto>()

        for (category in categories) {
            val pricesInCategory = priceList.filter {
                it.itemCategory == category.itemCategory
            }.toItemPriceWithIdDto().sortedBy { it.itemId }

            priceListByCategory.add(
                ItemPriceByCategoryWithIdDto(
                    category.itemCategoryId,
                    category.itemCategory,
                    pricesInCategory
                )
            )
        }

        return priceListByCategory
    }

    fun getPriceByItemId(itemId: Long): ItemPriceDto {
        val itemPrice =
            priceRepository.getItemPriceByItemId(itemId) ?: throw ResourceNotFoundException("Item not found")

        return itemPrice.toItemPriceDto()
    }

    fun getPriceListByItemCategory(categoryId: Long): ItemPriceByCategoryDto {
        return priceRepository.getPriceListByCategoryId(categoryId).toItemPriceByCategoryDto()
    }
}