package com.veygard.stockapi.presentation.screens.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.veygard.stockapi.R
import com.veygard.stockapi.databinding.ItemStockBinding
import com.veygard.stockapi.domain.model.StockItem


class StockListAdapter(
    private val listener: Listener,
    private val context: Context
) : ListAdapter<StockItem, StockListAdapter.StockItemViewHolder>(StockItemDiffCallback()) {


    interface Listener {
        fun itemClick(item: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            ItemStockBinding.inflate(layoutInflater, parent, false)
        return StockItemViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: StockItemViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }


    class StockItemViewHolder(
        private val binding: ItemStockBinding,
        private val context: Context
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: StockItem, listener: Listener) {
            binding.apply {
                tvStockName.text = item.checkName
                tvBarcode.text = context.getString(R.string.stock_item_barcode, item.barcode.toString())
                tvPrice.text = context.getString(R.string.stock_item_price, item.price.toString())
                tvRemaining.text = item.remainingStocks.toString()
                stockItemContainer.setOnClickListener {
                    listener.itemClick(item.id)
                }
            }
        }
    }
}