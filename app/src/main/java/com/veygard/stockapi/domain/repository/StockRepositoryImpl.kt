package com.veygard.stockapi.domain.repository

import com.veygard.stockapi.data.api.StockApi

class StockRepositoryImpl(stockApi: StockApi) : StockRepository {
    override suspend fun getStock() {}
}