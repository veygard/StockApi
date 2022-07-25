package com.veygard.stockapi.domain.repository

import okhttp3.ResponseBody

interface StockRepository {
    suspend fun getStock(): ResponseBody?
}