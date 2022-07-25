package com.veygard.stockapi.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veygard.stockapi.domain.model.StockItem
import com.veygard.stockapi.domain.response.StockRepositoryResponse.Error
import com.veygard.stockapi.domain.response.StockRepositoryResponse.Success
import com.veygard.stockapi.domain.usecase.GetStocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(val getStocksUseCase: GetStocksUseCase) : ViewModel() {

    private var originalList: List<StockItem>? = null
    private val _state = MutableLiveData<StockStateVM?>(null)
    val state: LiveData<StockStateVM?> = _state

    init {
        getStocks()
    }

    fun getStocks() {
        viewModelScope.launch {
            _state.value = StockStateVM.Loading
            when (val result = getStocksUseCase.execute()) {
                is Success -> {
                    originalList = result.stocks
                    _state.value = StockStateVM.GotData(result.stocks)
                }
                Error -> _state.value = StockStateVM.Error
            }
        }
    }

    fun getItemById(id:Int?): StockItem?= originalList?.single { it.id == id }
}