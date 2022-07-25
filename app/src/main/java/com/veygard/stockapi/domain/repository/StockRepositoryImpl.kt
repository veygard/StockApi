package com.veygard.stockapi.domain.repository

import com.veygard.stockapi.data.api.StockApi
import com.veygard.stockapi.domain.response.StockRepositoryResponse

class StockRepositoryImpl(private val stockApi: StockApi) : StockRepository {
    override suspend fun getStock(): StockRepositoryResponse {
        val call = stockApi.getStocks()
        return when {
            call.isSuccessful -> {
                StockRepositoryResponse.Success(emptyList())
            }
            else -> {
                StockRepositoryResponse.Error
            }
        }
    }
}