package com.veygard.stockapi.presentation.viewmodel

import com.veygard.stockapi.domain.model.StockItem

sealed class StockStateVM {
    data class GotData(val stocks: List<StockItem>) : StockStateVM()
    object Loading : StockStateVM()
    object Error : StockStateVM()
    object NothingFound : StockStateVM()
}