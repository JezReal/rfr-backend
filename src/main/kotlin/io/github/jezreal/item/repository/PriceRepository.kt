package io.github.jezreal.item.repository

import io.github.jezreal.item.dto.ItemPriceDto
import io.github.jezreal.item.model.ItemPriceModel
import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
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

        return transaction {
            Items.insert {
                it[itemName] = itemPriceDto.itemName
                it[Items.itemCategoryId] = itemCategoryId
                it[pricePerUnitLabel] = itemPriceDto.pricePerUnitLabel
                it[pricePerUnit] = itemPriceDto.pricePerUnit
                it[pricePerBag] = itemPriceDto.pricePerBag
            } get Items.itemId
        }
    }

    fun getPriceList(): List<ItemPriceModel> {
        return transaction {
            Items.join(ItemCategories, JoinType.INNER, additionalConstraint = {
                Items.itemCategoryId eq ItemCategories.itemCategoryId
            })
                .selectAll().map {
                    ItemPriceModel(
                        it[Items.itemId],
                        it[Items.itemCategoryId],
                        it[ItemCategories.itemCategory],
                        it[Items.itemName],
                        it[Items.pricePerUnitLabel],
                        it[Items.pricePerUnit],
                        it[Items.pricePerBag]
                    )
                }
        }
    }

    fun getItemPriceByItemId(itemId: Long): ItemPriceModel? {
        return transaction {
            Items.join(ItemCategories, JoinType.INNER, additionalConstraint = {
                Items.itemCategoryId eq ItemCategories.itemCategoryId
            })
                .select { Items.itemId eq itemId }.firstOrNull()?.let {
                    ItemPriceModel(
                        it[Items.itemId],
                        it[Items.itemCategoryId],
                        it[ItemCategories.itemCategory],
                        it[Items.itemName],
                        it[Items.pricePerUnitLabel],
                        it[Items.pricePerUnit],
                        it[Items.pricePerBag]
                    )
                }
        }
    }

    fun getPriceListByCategoryId(categoryId: Long): List<ItemPriceModel> {
        return transaction {
            Items.join(ItemCategories, JoinType.INNER, additionalConstraint = {
                Items.itemCategoryId eq ItemCategories.itemCategoryId
            })
                .select { ItemCategories.itemCategoryId eq categoryId }
                .map {
                    ItemPriceModel(
                        it[Items.itemId],
                        it[Items.itemCategoryId],
                        it[ItemCategories.itemCategory],
                        it[Items.itemName],
                        it[Items.pricePerUnitLabel],
                        it[Items.pricePerUnit],
                        it[Items.pricePerBag]
                    )
                }
        }
    }
}