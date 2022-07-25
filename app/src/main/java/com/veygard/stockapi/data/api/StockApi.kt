package com.veygard.stockapi.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface StockApi {
    @GET("test/catalog.spr")
    suspend fun getStocks(): Response<String>
}