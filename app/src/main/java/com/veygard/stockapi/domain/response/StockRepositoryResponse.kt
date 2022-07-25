package com.veygard.stockapi.domain.response

import com.veygard.stockapi.domain.model.StockItem

sealed class StockRepositoryResponse {
    data class Success(val stocks: List<StockItem>) : StockRepositoryResponse()
    object Error : StockRepositoryResponse()
}