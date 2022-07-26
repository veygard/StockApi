package com.veygard.stockapi.presentation.screens.list.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.veygard.stockapi.presentation.models.RowType
import com.veygard.stockapi.presentation.models.StockItemWithShimmer


class StockListAdapter(
    private val listener: Listener,
    private val context: Context
) : ListAdapter<StockItemWithShimmer, StockTypesViewHolder>(StockItemDiffCallback()) {

    interface Listener {
        fun itemClick(itemId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockTypesViewHolder {
        return when (RowType.values()[viewType]) {
            RowType.StockItem -> {
                StockItemViewHolder.from(parent, context)
            }
            RowType.Shimmer -> {
                ShimmerViewHolder.from(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: StockTypesViewHolder, position: Int) =
        holder.bind(getItem(position), listener = listener)

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is StockItemWithShimmer.Item -> 0
            StockItemWithShimmer.Shimmer -> 1
        }
    }
}