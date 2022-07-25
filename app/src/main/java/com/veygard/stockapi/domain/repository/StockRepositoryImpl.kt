package com.veygard.stockapi.domain.repository

import com.veygard.stockapi.data.api.StockApi
import okhttp3.ResponseBody
import retrofit2.Response

class StockRepositoryImpl(private val stockApi: StockApi) : StockRepository {
    override suspend fun getStock(): ResponseBody? {
        return stockApi.getStocks().body()
    }
}