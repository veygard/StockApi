package com.veygard.stockapi.domain.repository

import com.veygard.stockapi.data.api.StockApi

class StockRepositoryImpl(private val stockApi: StockApi) : StockRepository {
    override suspend fun getStock() {
        val responseBody = stockApi.getStocks()
    }
}