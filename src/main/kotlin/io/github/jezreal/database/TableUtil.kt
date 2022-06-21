package io.github.jezreal.database

import at.favre.lib.crypto.bcrypt.BCrypt
import io.github.jezreal.configuration.Configuration
import io.github.jezreal.constants.AccountTypeConstants
import io.github.jezreal.item.repository.ItemRepository
import io.github.jezreal.pricelist.dto.ItemPriceDto
import io.github.jezreal.tables.auth.AccountTypes
import io.github.jezreal.tables.auth.Credentials
import io.github.jezreal.tables.item.ItemCategories
import io.github.jezreal.tables.item.Items
import io.github.jezreal.tables.price.Prices
import io.github.jezreal.tables.store.Stores
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun insertAccountTypes() {
    val accountTypes = listOf("store", "admin")
    transaction {
        AccountTypes.batchInsert(accountTypes, shouldReturnGeneratedValues = false)
        { accountTypeName ->
            this[AccountTypes.accountType] = accountTypeName
        }
    }

}

fun insertTestAccount() {
    val storeAccount = Configuration.getStoreAccountConfiguration()
    val hashedPassword = BCrypt.withDefaults().hashToString(12, storeAccount.password.toCharArray())
    println(storeAccount.username)
    val credentialId = transaction {
        Credentials.insert {
            it[username] = storeAccount.username
            it[password] = hashedPassword
            it[accountTypeId] = AccountTypeConstants.STORE.toLong()
        } get Credentials.credentialId
    }

    transaction {
        Stores.insert {
            it[storeName] = storeAccount.storeName
            it[storeAddress] = storeAccount.storeAddress
            it[Stores.credentialId] = credentialId
        }
    }
}

fun insertAdminAccount() {
    val adminAccount = Configuration.getAdminAccountConfiguration()
    val hashedPassword = BCrypt.withDefaults().hashToString(12, adminAccount.password.toCharArray())

    transaction {
        Credentials.insert {
            it[username] = adminAccount.username
            it[password] = hashedPassword
            it[accountTypeId] = AccountTypeConstants.ADMIN.toLong()
        } get Credentials.credentialId
    }
}

fun insertInitialPriceList() {
    val itemRepository = ItemRepository

    val initialPriceList = listOf(
        ItemPriceDto(
            "Sagupaan",
            "Chick Booster",
            "Price/Pack",
            44.00,
            1032.00
        ),
        ItemPriceDto(
            "Sagupaan",
            "Baby Stag Developer",
            "Price/Pack",
            44.00,
            1032.00
        ),
        ItemPriceDto(
            "Sagupaan",
            "Stag Developer",
            "Price/Pack",
            43.00,
            1008.00
        ),
        ItemPriceDto(
            "Sagupaan",
            "High Action",
            "Price/Pack",
            40.00,
            936.00
        ),
        ItemPriceDto(
            "Sagupaan",
            "Ready To Fight",
            "Price/Pack",
            50.00,
            1076.00
        ),
        ItemPriceDto(
            "Sagupaan",
            "Winning Line",
            "Price/Pack",
            54.00,
            1272.00
        ),

        ItemPriceDto(
            "Thunderbird",
            "Baby Stag Booster",
            "Price/Pack",
            44.00,
            1032.00
        ),
        ItemPriceDto(
            "Thunderbird",
            "Stag Dev. 1-4",
            "Price/Pack",
            43.00,
            1008.00
        ),
        ItemPriceDto(
            "Thunderbird",
            "Stag Dev. 4+",
            "Price/Pack",
            39.00,
            912.00
        ),
        ItemPriceDto(
            "Thunderbird",
            "Enertone",
            "Price/Pack",
            37.00,
            854.00
        ),
        ItemPriceDto(
            "Thunderbird",
            "Platinum",
            "Price/Pack",
            57.00,
            1344.00
        ),
        ItemPriceDto(
            "Thunderbird",
            "Platinum (TC)",
            "Price/Pack",
            57.00,
            1380.00
        ),
        ItemPriceDto(
            "Thunderbird",
            "Successor",
            "Price/Pack",
            38.00,
            888.00
        ),
        ItemPriceDto(
            "Thunderbird",
            "GMP-3",
            "Price/Pack",
            31.00,
            1430.00
        ),
        ItemPriceDto(
            "Thunderbird",
            "GMP-4",
            "Price/Pack",
            29.00,
            1380.00
        ),

        ItemPriceDto(
            "Integra",
            "Integra 1",
            "Price/KG",
            37.00,
            1790.00
        ),
        ItemPriceDto(
            "Integra",
            "Integra 2",
            "Price/KG",
            36.00,
            1730.00
        ),
        ItemPriceDto(
            "Integra",
            "Integra 2.5",
            "Price/KG",
            35.00,
            1745.00
        ),
        ItemPriceDto(
            "Integra",
            "Integra 3",
            "Price/KG",
            33.00,
            1565.00
        ),

        ItemPriceDto(
            "Dog Food",
            "BeefPro Adult",
            "Price/KG",
            130.00,
            2750.00
        ),
        ItemPriceDto(
            "Dog Food",
            "BeefPro Puppy",
            "Price/KG",
            150.00,
            3235.00
        ),
        ItemPriceDto(
            "Dog Food",
            "Pedigree Adult",
            "Price/KG",
            125.00,
            2345.00
        ),
        ItemPriceDto(
            "Dog Food",
            "Pedigree Puppy",
            "Price/KG",
            158.00,
            2250.00
        ),
        ItemPriceDto(
            "Dog Food",
            "Royce Adult",
            "Price/KG",
            72.00,
            1520.00
        ),
        ItemPriceDto(
            "Dog Food",
            "Royce Puppy",
            "Price/KG",
            90.00,
            1890.00
        ),
        ItemPriceDto(
            "Dog Food",
            "Top Breed Adult",
            "Price/KG",
            70.00,
            1335.00
        ),
        ItemPriceDto(
            "Dog Food",
            "Top Breed Puppy",
            "Price/KG",
            85.00,
            1590.00
        ),
        ItemPriceDto(
            "Dog Food",
            "Dogi Beef",
            "Price/KG",
            73.00,
            1560.00
        ),

        ItemPriceDto(
            "Cat Food",
            "Princess",
            "Price/KG",
            148.00,
            3280.00
        ),
        ItemPriceDto(
            "Cat Food",
            "Catherine",
            "Price/KG",
            115.00,
            2420.00
        ),
        ItemPriceDto(
            "Cat Food",
            "Cuties",
            "Price/KG",
            115.00,
            2420.00
        ),
        ItemPriceDto(
            "Cat Food",
            "Aozi",
            "Price/KG",
            160.00,
            2810.00
        ),
        ItemPriceDto(
            "Cat Food",
            "Powercat Kitten",
            "Price/KG",
            195.00,
            1350.00
        ),
        ItemPriceDto(
            "Cat Food",
            "Powercat Tuna",
            "Price/KG",
            170.00,
            1350.00
        ),

        ItemPriceDto(
            "Grains",
            "Flyer (Hagibis)",
            "Price/KG",
            40.00,
            965.00
        ),
        ItemPriceDto(
            "Grains",
            "Breeder (Hagibis)",
            "Price/KG",
            39.00,
            950.00
        ),
        ItemPriceDto(
            "Grains",
            "Flyer (Kamandag)",
            "Price/KG",
            33.00,
            760.00
        ),
        ItemPriceDto(
            "Grains",
            "Concentrate (Kam)",
            "Price/KG",
            29.00,
            705.00
        ),
        ItemPriceDto(
            "Grains",
            "Concentrate (Everlife)",
            "Price/KG",
            29.00,
            720.00
        ),
        ItemPriceDto(
            "Grains",
            "Slasher Green (Kam)",
            "Price/KG",
            33.00,
            800.00
        ),
        ItemPriceDto(
            "Grains",
            "Mega Con (Kam)",
            "Price/KG",
            34.00,
            815.00
        ),
        ItemPriceDto(
            "Grains",
            "Diamond (Kam)",
            "Price/KG",
            34.00,
            830.00
        ),
        ItemPriceDto(
            "Grains",
            "Dynamix (Kam)",
            "Price/KG",
            33.00,
            755.00
        ),
        ItemPriceDto(
            "Grains",
            "Warlord Premium Mix",
            "Price/KG",
            34.00,
            825.00
        ),
        ItemPriceDto(
            "Grains",
            "Hagibis Super E.F",
            "Price/KG",
            38.00,
            940.00
        ),
        ItemPriceDto(
            "Grains",
            "Enermax",
            "Price/KG",
            35.00,
            850.00
        ),
        ItemPriceDto(
            "Grains",
            "Birdseed Mix (Ukraine)",
            "Price/KG",
            70.00,
            1350.00
        ),
        ItemPriceDto(
            "Grains",
            "Birdseed Plain (Ukraine)",
            "Price/KG",
            50.00,
            1050.00
        ),
        ItemPriceDto(
            "Grains",
            "Crack Corn (Tchoice)",
            "Price/KG",
            34.00,
            1300.00
        ),
        ItemPriceDto(
            "Grains",
            "Crack Grits (Hagibis)",
            "Price/KG",
            34.00,
            950.00
        ),
    )

    for (item in initialPriceList) {
        var itemCategoryId = itemRepository.getItemByItemCategoryName(item.itemCategory)?.itemCategoryId

        if (itemCategoryId == null) {
            itemCategoryId = transaction {
                ItemCategories.insert {
                    it[itemCategory] = item.itemCategory
                } get ItemCategories.itemCategoryId
            }
        }

        val priceId = transaction {
            Prices.insert {
                it[pricePerUnitLabel] = item.pricePerUnitLabel
                it[pricePerUnit] = item.pricePerUnit
                it[pricePerBag] = item.pricePerBag
            } get Prices.priceId
        }

        transaction {
            Items.insert {
                it[itemName] = item.itemName
                it[Items.itemCategoryId] = itemCategoryId
                it[Items.priceId] = priceId
            }
        }
    }
}