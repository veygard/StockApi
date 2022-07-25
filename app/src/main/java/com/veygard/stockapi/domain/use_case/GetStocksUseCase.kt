package com.veygard.stockapi.domain.use_case

import com.veygard.stockapi.domain.repository.StockRepository

class GetStocksUseCase(private val stockRepository: StockRepository) {
    suspend fun execute(){}
}