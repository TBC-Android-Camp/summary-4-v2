package com.example.summary_4v2.screens.fragments

import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.summary_4v2.adapter.ItemRecyclerViewAdapter
import com.example.summary_4v2.base.BaseFragment
import com.example.summary_4v2.databinding.FragmentMainBinding
import com.example.summary_4v2.model.MainViewModel
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private lateinit var adapter: ItemRecyclerViewAdapter

    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun init() {
        setUpRecyclerView()
    }

    override fun listeners() {
        binding.imageButton.setOnClickListener {
            search()
        }
        showAllItems()
    }

    private fun setUpRecyclerView() {
        adapter = ItemRecyclerViewAdapter()
        binding.recyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.itemsFlow.collect { items ->
                adapter.submitList(items)
            }
        }
    }

    private fun search() =
        with(binding) {
            val filteredChats = viewModel.itemsFlow.value.filter { item ->
                item.owner.contains(etSearch.text.toString(), true)
            }
            adapter.submitList(filteredChats)
        }

    private fun showAllItems() =
        with(binding) {
            etSearch.doAfterTextChanged {
                adapter.submitList(viewModel.itemsFlow.value)
            }
        }
}