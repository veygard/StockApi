package com.veygard.stockapi.di

import com.veygard.stockapi.data.api.StockApi
import com.veygard.stockapi.domain.repository.StockRepository
import com.veygard.stockapi.domain.repository.StockRepositoryImpl
import com.veygard.stockapi.domain.usecase.GetStocksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun provideStockRepository(
        stockApi: StockApi
    ): StockRepository = StockRepositoryImpl(stockApi)

    @Provides
    @Singleton
    fun provideGetStocksUseCase(
        stockRepository: StockRepository,
    ): GetStocksUseCase = GetStocksUseCase(
        stockRepository
    )
}