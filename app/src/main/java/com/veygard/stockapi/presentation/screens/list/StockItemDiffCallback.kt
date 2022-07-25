package com.veygard.stockapi.presentation.screens.list

import androidx.recyclerview.widget.DiffUtil
import com.veygard.stockapi.domain.model.StockItem

class StockItemDiffCallback : DiffUtil.ItemCallback<StockItem>() {
    override fun areItemsTheSame(oldItem: StockItem, newItem: StockItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: StockItem, newItem: StockItem): Boolean =
        oldItem == newItem
}