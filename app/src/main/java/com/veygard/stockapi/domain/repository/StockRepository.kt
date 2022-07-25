package com.veygard.stockapi.domain.repository

interface StockRepository {
    suspend fun getStock()
}