package com.veygard.stockapi.domain.repository

import com.veygard.stockapi.data.api.StockApi
import com.veygard.stockapi.domain.model.StockAttribute
import com.veygard.stockapi.domain.model.StockItem
import com.veygard.stockapi.domain.repository.StockRepositoryConstants.attrIdPattern
import com.veygard.stockapi.domain.repository.StockRepositoryConstants.attrValuePattern
import com.veygard.stockapi.domain.repository.StockRepositoryConstants.attribute
import com.veygard.stockapi.domain.repository.StockRepositoryConstants.stockIdPattern
import com.veygard.stockapi.domain.response.StockRepositoryResponse

class StockRepositoryImpl(private val stockApi: StockApi) : StockRepository {
    override suspend fun getStock(): StockRepositoryResponse {
        val call = stockApi.getStocks()
        return when {
            call.isSuccessful -> {
                return call.body()?.let {
                    getStockItemsFromApiBody(it)
                    StockRepositoryResponse.Success(emptyList())
                }
                    ?: kotlin.run {
                        StockRepositoryResponse.Error
                    }
            }
            else -> {
                StockRepositoryResponse.Error
            }
        }
    }

    private fun getStockItemsFromApiBody(body: String) {
        val items = mutableListOf<StockItem>()
        val lines = body.split("\r\n").map { it.trim() }
        val mapOfAttr: MutableMap<Int, MutableList<StockAttribute>> = mutableMapOf()
        lines.forEach { line ->
            when {

                /*from string to StockAttribute, and added it to mapOfAttr*/
                line.contains(attribute) -> getItemAttribute(line)?.let { attr ->
                    mapOfAttr[attr.itemId]?.let {
                        it.add(
                            attr
                        )
                    } ?: kotlin.run {
                        mapOfAttr[attr.itemId] = mutableListOf(attr)
                    }
                }
            }
        }
    }

    private fun getItemAttribute(line: String): StockAttribute? {
        return try {
            val stockIdPattern = Regex(stockIdPattern, RegexOption.IGNORE_CASE)
            val attrIdPattern = Regex(attrIdPattern, RegexOption.IGNORE_CASE)
            val attrValuePattern = Regex(attrValuePattern, RegexOption.IGNORE_CASE)

            val id = stockIdPattern.find(line)?.destructured?.toList()?.firstOrNull()
            val attrId = attrIdPattern.find(line)?.destructured?.toList()?.firstOrNull()
            val value = attrValuePattern.find(line)?.destructured?.toList()?.firstOrNull()

            StockAttribute(
                itemId = id?.toInt() ?: 0,
                attrId = attrId?.toInt() ?: 0,
                attrValue = value.toString()
            )
        } catch (e: Exception) {
            return null
        }
    }
}