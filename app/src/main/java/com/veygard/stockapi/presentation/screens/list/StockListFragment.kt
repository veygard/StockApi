package com.veygard.stockapi.presentation.screens.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.veygard.stockapi.R
import com.veygard.stockapi.databinding.FragmentStockListBinding
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
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                StockStateVM.Error -> {}
                is StockStateVM.GotData -> {
                    adapter?.submitList(state.stocks)
                }
                StockStateVM.Loading -> {}
                else -> {}
            }
        }
    }

    private fun initAdapter() {
        val reversLayoutManager = LinearLayoutManager(context)
        binding.stocksRecyclerHolder.layoutManager = reversLayoutManager
        adapter = StockListAdapter(
            listener = object : StockListAdapter.Listener {
                override fun itemClick(item: Int) {
                    
                }
            },
            context = requireContext()
        )
        binding.stocksRecyclerHolder.adapter = adapter
    }
}