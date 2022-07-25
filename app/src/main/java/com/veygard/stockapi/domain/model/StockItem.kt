package com.veygard.stockapi.domain.model

data class StockItem(
    val id: Int,
    val barcode: String? = null,
    val positionName: String? = null,
    val checkName: String? = null,
    val price: Double? = null,
    val remainingStocks: Double? = null,
    var attributes: List<StockAttribute>? = null
)