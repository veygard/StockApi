package com.veygard.stockapi.presentation.screens.list.adapters

import androidx.recyclerview.widget.DiffUtil
import com.veygard.stockapi.domain.model.StockItem
import com.veygard.stockapi.presentation.models.StockItemWithShimmer

class StockItemDiffCallback : DiffUtil.ItemCallback<StockItemWithShimmer>() {
    override fun areItemsTheSame(oldItem: StockItemWithShimmer, newItem: StockItemWithShimmer): Boolean {
        var isSame = false
        when {
            oldItem is StockItemWithShimmer.Item && newItem is StockItemWithShimmer.Item -> {
                isSame = oldItem.item.id == newItem.item.id
            }
            oldItem is StockItemWithShimmer.Shimmer && newItem is StockItemWithShimmer.Shimmer -> isSame =
                true
        }
        return isSame
    }

    override fun areContentsTheSame(oldItem: StockItemWithShimmer, newItem: StockItemWithShimmer): Boolean =
        oldItem == newItem
}