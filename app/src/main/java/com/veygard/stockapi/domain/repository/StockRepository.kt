package com.veygard.stockapi.domain.repository

import com.veygard.stockapi.domain.response.StockRepositoryResponse

interface StockRepository {
    suspend fun getStock(): StockRepositoryResponse
}