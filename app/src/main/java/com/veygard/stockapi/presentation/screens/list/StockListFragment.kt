package com.veygard.stockapi.presentation.screens.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.veygard.stockapi.R
import com.veygard.stockapi.databinding.FragmentStockListBinding
import com.veygard.stockapi.presentation.models.StockItemWithShimmer
import com.veygard.stockapi.presentation.models.toItemShimmerList
import com.veygard.stockapi.presentation.screens.item.StockItemFragment.Companion.STOCK_ITEM_ID_TAG
import com.veygard.stockapi.presentation.screens.list.adapters.StockListAdapter
import com.veygard.stockapi.presentation.viewmodel.StockStateVM
import com.veygard.stockapi.presentation.viewmodel.StockViewModel


class StockList : Fragment() {

    private val viewModel: StockViewModel by activityViewModels()
    private val binding: FragmentStockListBinding by viewBinding()
    private var adapter: StockListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_stock_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initAdapter()
        if (viewModel.stockAreEmpty()) {
            Log.d("testing_something", "stockAreEmpty")
            viewModel.getStocks()
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                StockStateVM.Error -> {
                    Log.d("testing_something", "StockStateVM.Error")
                    findNavController().navigate(R.id.action_stockListFragment_to_errorFragment)
                }
                is StockStateVM.GotData -> {
                    adapter?.submitList(state.stocks.toItemShimmerList())
                }
                StockStateVM.Loading -> {
                    adapter?.submitList(
                        listOf(
                            StockItemWithShimmer.Shimmer,
                            StockItemWithShimmer.Shimmer,
                            StockItemWithShimmer.Shimmer,
                            StockItemWithShimmer.Shimmer,
                            StockItemWithShimmer.Shimmer,
                            StockItemWithShimmer.Shimmer
                        )
                    )
                }
                else -> {}
            }
        }
    }

    private fun initAdapter() {
        val reversLayoutManager = LinearLayoutManager(context)
        binding.stocksRecyclerHolder.layoutManager = reversLayoutManager
        adapter = StockListAdapter(
            listener = object : StockListAdapter.Listener {
                override fun itemClick(itemId: Int) {
                    findNavController().navigate(
                        R.id.action_stockListFragment_to_stockItemFragment,
                        bundleOf(
                            STOCK_ITEM_ID_TAG to itemId
                        )
                    )
                }
            },
            context = requireContext()
        )
        binding.stocksRecyclerHolder.adapter = adapter
    }
}