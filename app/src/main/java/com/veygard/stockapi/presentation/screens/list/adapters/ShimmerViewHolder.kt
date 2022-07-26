package com.veygard.stockapi.presentation.screens.list.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.veygard.stockapi.databinding.ItemStockShimmerBinding
import com.veygard.stockapi.presentation.models.StockItemWithShimmer

class ShimmerViewHolder(
    private val binding: ItemStockShimmerBinding,
) : StockTypesViewHolder(binding) {

    companion object{
        fun from(parent: ViewGroup): ShimmerViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding =
                ItemStockShimmerBinding.inflate(layoutInflater, parent, false)
            return ShimmerViewHolder(binding)
        }
    }

    override fun bind(item: StockItemWithShimmer, listener: StockListAdapter.Listener?) {
        binding.stockShimmerLayout.startShimmer()
    }

}
