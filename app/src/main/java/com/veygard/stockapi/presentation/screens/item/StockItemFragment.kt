package com.veygard.stockapi.presentation.screens.item

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.veygard.stockapi.R
import com.veygard.stockapi.databinding.FragmentStockItemBinding
import com.veygard.stockapi.domain.model.StockItem
import com.veygard.stockapi.presentation.viewmodel.StockViewModel

class StockItemFragment : Fragment() {
    private val viewModel: StockViewModel by activityViewModels()
    private val binding: FragmentStockItemBinding by viewBinding()

    private var itemId: Int? = null
    private var stockItem: StockItem? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stock_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemId= arguments?.getInt(STOCK_ITEM_ID_TAG)
        stockItem = viewModel.getItemById(itemId)
        binding.itemTest.text = stockItem?.checkName
    }

    companion object{
        const val STOCK_ITEM_ID_TAG = "stockItemIdTag"
    }
}