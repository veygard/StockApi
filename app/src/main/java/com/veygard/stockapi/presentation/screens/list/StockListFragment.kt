package com.veygard.stockapi.presentation.screens.list

import android.os.Bundle
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
import com.veygard.stockapi.util.safeNavigate
import com.veygard.stockapi.util.toggleSearchViewIconColor
import com.veygard.stockapi.util.toggleVisibility


class StockListFragment : Fragment() {

    private val viewModel: StockViewModel by activityViewModels()
    private val binding: FragmentStockListBinding by viewBinding()
    private var adapter: StockListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_stock_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.stockAreEmpty()) {
            viewModel.getStocks()
        }
        observeViewModel()
        initAdapter()
        cancelButtonListener()
        searchViewListener()
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                StockStateVM.Error -> {
                    val action =
                        StockListFragmentDirections.actionStockListFragmentToErrorFragment()
                    findNavController().safeNavigate(action)
                }
                is StockStateVM.GotData -> {
                    adapter?.submitList(state.stocks.toItemShimmerList())
                }
                StockStateVM.Loading -> {
                    val shimmers: MutableList<StockItemWithShimmer> =
                        MutableList(10) { StockItemWithShimmer.Shimmer }
                    adapter?.submitList(shimmers)
                }
                StockStateVM.NothingFound -> {
                    val nothingFound: MutableList<StockItemWithShimmer> =
                        mutableListOf(StockItemWithShimmer.NothingFound)
                    adapter?.submitList(nothingFound)
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

    private fun cancelButtonListener() {
        binding.cancelButton.setOnClickListener {
            binding.searchBar.setQuery("", false)
            binding.searchBar.clearFocus()
        }
    }

    private fun searchViewListener() {
        binding.searchBar.queryHint = resources.getString(R.string.search_field_placeholder)
        binding.searchBar.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(text: String): Boolean {
                viewModel.filterItemsBySearch(text)
                toggleVisibility(text.isEmpty(), binding.cancelButton)
                toggleSearchViewIconColor(text.isNotEmpty(), requireContext(), binding.searchIcon)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.filterItemsBySearch(query ?: "")
                toggleVisibility(query?.isEmpty() ?: true, binding.cancelButton)
                toggleSearchViewIconColor(
                    query?.isNotEmpty() ?: false,
                    requireContext(),
                    binding.searchIcon
                )
                return false
            }
        })
    }
}