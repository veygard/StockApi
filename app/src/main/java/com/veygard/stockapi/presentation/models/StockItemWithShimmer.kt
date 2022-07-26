package com.veygard.stockapi.presentation.models

import android.graphics.Movie
import com.veygard.stockapi.domain.model.StockItem


sealed class StockItemWithShimmer(
    val rowType: RowType,
    open val item: StockItem? = null
){
    data class Item(override val item: StockItem) : StockItemWithShimmer(RowType.StockItem, item = item)
    object Shimmer : StockItemWithShimmer(RowType.Shimmer)
//    object NothingFound: StockItemWithShimmer(RowType.NothingFound)
}

enum class RowType {
    StockItem,
//    NothingFound,
    Shimmer
}

fun StockItem.toShimmerItem(): StockItemWithShimmer = StockItemWithShimmer.Item(this)
fun List<StockItem>.toItemShimmerList() : MutableList<StockItemWithShimmer> = this.map { it.toShimmerItem() }.toMutableList()
