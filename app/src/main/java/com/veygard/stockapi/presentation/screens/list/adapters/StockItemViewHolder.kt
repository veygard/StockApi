package com.veygard.stockapi.presentation.screens.list.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.veygard.stockapi.R
import com.veygard.stockapi.databinding.ItemStockBinding
import com.veygard.stockapi.presentation.models.StockItemWithShimmer

class StockItemViewHolder(
    private val binding: ItemStockBinding,
    private val context: Context
) : StockTypesViewHolder(binding) {

    override fun bind(item: StockItemWithShimmer, listener: StockListAdapter.Listener?) {
        val stockItem = (item as StockItemWithShimmer.Item).item

        binding.apply {
            tvStockName.text = stockItem.checkName
            tvBarcode.text = context.getString(R.string.stock_item_barcode, stockItem.barcode.toString())
            tvPrice.text = context.getString(R.string.stock_item_price, stockItem.price.toString())
            tvRemaining.text = stockItem.remainingStocks.toString()
            stockItemContainer.setOnClickListener {
                Log.d("testing_something","item: ${stockItem.checkName}")
                listener?.itemClick(stockItem.id)
            }
        }
    }

    companion object{
        fun from(parent: ViewGroup, context: Context): StockItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ItemStockBinding.inflate(layoutInflater, parent, false)
            return StockItemViewHolder(binding, context)
        }
    }
}