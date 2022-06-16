package io.github.jezreal.tables.price

import org.jetbrains.exposed.sql.Table

object Prices : Table() {
    val priceId = long("price_id").autoIncrement()
    val pricePerUnitLabel = varchar("price_per_unit_label", 500)
    val pricePerUnit = double("price_per_unit")
    val pricePerBag = double("price_per_bag")

    override val primaryKey: PrimaryKey
        get() = PrimaryKey(priceId)
}