package com.veygard.stockapi.domain.usecase

import com.veygard.stockapi.domain.repository.StockRepository

class GetStocksUseCase(private val stockRepository: StockRepository) {
    suspend fun execute() = stockRepository.getStock()
}