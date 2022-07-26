package com.veygard.stockapi.presentation.screens.list.adapters

import androidx.recyclerview.widget.RecyclerView
import com.veygard.stockapi.presentation.models.StockItemWithShimmer

abstract class StockTypesViewHolder(binding: androidx.viewbinding.ViewBinding) : RecyclerView.ViewHolder(binding.root){
    abstract fun bind(item: StockItemWithShimmer,  listener: StockListAdapter.Listener? = null)
}