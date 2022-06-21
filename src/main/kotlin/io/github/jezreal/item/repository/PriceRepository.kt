package io.github.jezreal.item.repository

import io.github.jezreal.item.dto.ItemPriceDto
import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import io.github.jezreal.tables.price.Prices
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

object PriceRepository {
    private val itemRepository = ItemRepository

    fun addItemToPriceList(itemPriceDto: ItemPriceDto): Long {
        var itemCategoryId = itemRepository.getItemByItemCategoryName(itemPriceDto.itemCategory)?.itemCategoryId

        if (itemCategoryId == null) {
            itemCategoryId = transaction {
                ItemCategories.insert {
                    it[itemCategory] = itemPriceDto.itemCategory
                } get ItemCategories.itemCategoryId
            }
        }

        val priceId = transaction {
            Prices.insert {
                it[pricePerUnitLabel] = itemPriceDto.pricePerUnitLabel
                it[pricePerUnit] = itemPriceDto.pricePerUnit
                it[pricePerBag] = itemPriceDto.pricePerBag
            } get Prices.priceId
        }

        return transaction {
            Items.insert {
                it[itemName] = itemPriceDto.itemName
                it[Items.itemCategoryId] = itemCategoryId
                it[Items.priceId] = priceId
            } get Items.itemId
        }
    }
}