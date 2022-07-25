package com.veygard.stockapi.domain.repository

import com.veygard.stockapi.data.api.StockApi
import com.veygard.stockapi.domain.model.StockAttribute
import com.veygard.stockapi.domain.model.StockItem
import com.veygard.stockapi.domain.repository.ConstantsRepository.attrIdPattern
import com.veygard.stockapi.domain.repository.ConstantsRepository.attrValuePattern
import com.veygard.stockapi.domain.repository.ConstantsRepository.attribute
import com.veygard.stockapi.domain.repository.ConstantsRepository.fullItemPattern
import com.veygard.stockapi.domain.repository.ConstantsRepository.stockIdPattern
import com.veygard.stockapi.domain.response.StockRepositoryResponse

class StockRepositoryImpl(private val stockApi: StockApi) : StockRepository {
    override suspend fun getStock(): StockRepositoryResponse {
        val call = stockApi.getStocks()
        return when {
            call.isSuccessful -> {
                return call.body()?.let {
                    StockRepositoryResponse.Success(getStockItemsFromApiBody(it))
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

    private fun getStockItemsFromApiBody(body: String): List<StockItem> {
        val items = mutableListOf<StockItem>()
        val lines = body.split("\r\n").map { it.trim() }
        val mapOfAttr: MutableMap<Int, MutableList<StockAttribute>> = mutableMapOf()
        lines.forEach { line ->
            when {
                /*from string to StockAttribute, and added it to mapOfAttr*/
                line.contains(attribute) -> getItemAttribute(line)?.let { attr ->
                    mapOfAttr[attr.itemId]?.add(
                        attr
                    ) ?: kotlin.run {
                        mapOfAttr[attr.itemId] = mutableListOf(attr)
                    }
                }
                /*trying to get StockItem from string*/
                else -> getItemFromString(line)?.let { items.add(it) }
            }
        }

        /*combine items and attr together*/
        mapOfAttr.forEach { attr ->
            items.singleOrNull { it.id == attr.key }?.attributes = attr.value
        }

        return items.toList()
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

    private fun getItemFromString(line: String): StockItem? {
        return try {
            val itemPattern = Regex(fullItemPattern, RegexOption.IGNORE_CASE)

            val id = itemPattern.find(line)?.destructured?.toList()?.get(0)?.toInt()
            val barcode = itemPattern.find(line)?.destructured?.toList()?.get(1)
            val positionName: String? = itemPattern.find(line)?.destructured?.toList()?.get(2)
            val checkName: String? = itemPattern.find(line)?.destructured?.toList()?.get(3)
            val price: Double? = itemPattern.find(line)?.destructured?.toList()?.get(4)?.toDouble()
            val remainingStocks: Double? =
                itemPattern.find(line)?.destructured?.toList()?.get(5)?.toDouble()

            StockItem(
                id = id!!,
                barcode = barcode,
                positionName = positionName,
                checkName = checkName,
                price = price,
                remainingStocks = remainingStocks,
            )
        } catch (e: Exception) {
            return null
        }
    }
}