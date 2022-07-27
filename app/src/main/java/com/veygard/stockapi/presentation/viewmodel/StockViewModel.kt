package com.veygard.stockapi.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veygard.stockapi.domain.model.StockItem
import com.veygard.stockapi.domain.response.StockRepositoryResponse.Error
import com.veygard.stockapi.domain.response.StockRepositoryResponse.Success
import com.veygard.stockapi.domain.usecase.GetStocksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(private val getStocksUseCase: GetStocksUseCase) : ViewModel() {

    private var originalList: List<StockItem>? = null
    private val _state = MutableLiveData<StockStateVM?>(null)
    val state: LiveData<StockStateVM?> = _state
    var requestIsProcessing: Boolean? = null

    init {
        getStocks()
    }

    fun getStocks() {
        viewModelScope.launch {
            _state.value = StockStateVM.Loading
            delay(1500) //demonstration shimmer
            requestIsProcessing = true
            when (val result = getStocksUseCase.execute()) {

                is Success -> {
                    originalList = result.stocks
                    _state.value = StockStateVM.GotData(result.stocks)
                }
                Error -> _state.value = StockStateVM.Error
            }
            requestIsProcessing = null
        }
    }

    fun getItemById(id: Int?): StockItem? = originalList?.single { it.id == id }

    fun filterItemsBySearch(value: String) {
        viewModelScope.launch {
            when {
                value.isEmpty() -> {
                    _state.value = StockStateVM.GotData(originalList ?: emptyList())
                }
                else -> {
                    val newList = originalList?.filter {
                        it.checkName?.contains(
                            value,
                            ignoreCase = true
                        ) == true
                    }
                        ?: emptyList()
                    when {
                        newList.isEmpty() -> _state.value = StockStateVM.NothingFound
                        newList.isNotEmpty() -> {
                            _state.value = StockStateVM.GotData(newList)
                        }
                    }
                }
            }
        }
    }

    fun stockAreEmpty() = originalList.isNullOrEmpty() && requestIsProcessing == null
}