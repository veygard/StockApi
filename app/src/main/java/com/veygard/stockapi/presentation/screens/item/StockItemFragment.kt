package com.veygard.stockapi.presentation.screens.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.veygard.stockapi.R
import com.veygard.stockapi.databinding.FragmentStockItemBinding
import com.veygard.stockapi.domain.model.StockItem
import com.veygard.stockapi.presentation.viewmodel.StockViewModel

const val ALCOHOL_TYPE_CODE = 22
const val ALCOHOL_CONTENT_CODE = 27

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
        itemId = arguments?.getInt(STOCK_ITEM_ID_TAG)
        stockItem = viewModel.getItemById(itemId)
        setupTextFields()
        setListeners()
    }

    private fun setListeners() {
        binding.ibBackItem.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupTextFields() {
        binding.apply {
            tvBarcodeItem.text = stockItem?.barcode
            tvNameItem.text = stockItem?.checkName
            tvRemainingValue.text = stockItem?.remainingStocks.toString()
            tvPriceItemValue.text =
                resources.getString(R.string.stock_item_price, stockItem?.price.toString())
            tvSumItemValue.text =
                resources.getString(R.string.stock_item_fragment_sum_value, getSum())
            tvProductItemValue.text =
                getProduct() ?: resources.getString(R.string.stock_item_fragment_no_data)
            tvAlcoholItemValue.text =
                getAlcoholContent() ?: resources.getString(R.string.stock_item_fragment_no_data)
        }
    }

    private fun getSum(): String {
        val price = stockItem?.price
        val stocking = stockItem?.remainingStocks
        var sum: String? = null
        price?.let { p -> stocking?.let { s -> sum = (p * s).toString() } }
        return sum ?: "0"
    }

    private fun getProduct() =
        stockItem?.attributes?.singleOrNull { it.attrId == ALCOHOL_TYPE_CODE }?.attrValue

    private fun getAlcoholContent() =
        stockItem?.attributes?.singleOrNull { it.attrId == ALCOHOL_CONTENT_CODE }?.attrValue

    companion object {
        const val STOCK_ITEM_ID_TAG = "stockItemIdTag"
    }
}