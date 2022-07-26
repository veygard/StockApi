package com.veygard.stockapi.presentation.screens.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.veygard.stockapi.databinding.ItemStockNothingFoundedBinding
import com.veygard.stockapi.databinding.ItemStockShimmerBinding
import com.veygard.stockapi.presentation.models.StockItemWithShimmer

class NothingFoundViewHolder(
    private val binding: ItemStockNothingFoundedBinding,
) : StockTypesViewHolder(binding) {

    companion object{
        fun from(parent: ViewGroup): NothingFoundViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ItemStockNothingFoundedBinding.inflate(layoutInflater, parent, false)
            return NothingFoundViewHolder(binding)
        }
    }

    override fun bind(item: StockItemWithShimmer, listener: StockListAdapter.Listener?) {}
}
