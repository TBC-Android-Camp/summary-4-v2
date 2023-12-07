package com.example.summary_4v2.screens.fragments

import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.summary_4v2.adapter.ItemRecyclerViewAdapter
import com.example.summary_4v2.base.BaseFragment
import com.example.summary_4v2.databinding.FragmentMainBinding
import com.example.summary_4v2.model.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
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

    private fun setUpRecyclerView() =
        with(binding) {
            adapter = ItemRecyclerViewAdapter()
            recyclerView.adapter = adapter
            progressBar.visibility = View.VISIBLE

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.itemsFlow.collect { items ->
                        adapter.submitList(items)
                        progressBar.visibility = View.GONE
                    }
                }
            }
        }

    private fun search() =
        with(binding) {
            val filteredChats = viewModel.itemsFlow.value.filter { item ->
                item.owner.contains(etSearch.text.toString(), true)
            }

            if (filteredChats.isEmpty()) {
                Snackbar.make(binding.root, "მომხმარებელი არ იქნა ნაპოვნი", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                adapter.submitList(filteredChats)
            }
        }

    private fun showAllItems() =
        with(binding) {
            etSearch.addTextChangedListener {
                adapter.submitList(viewModel.itemsFlow.value)
            }
        }
}