package com.example.summary_4v2.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.summary_4v2.network.ApiResult
import com.example.summary_4v2.network.RetrofitInstance
import com.example.summary_4v2.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel(
    private val repository: MainRepository = MainRepository(RetrofitInstance.itemApi)
) : ViewModel() {
    private val _itemsFlow = MutableStateFlow<List<Item>>(emptyList())
    var itemsFlow: StateFlow<List<Item>> = _itemsFlow

    init {
        data()
    }

    private fun data() = viewModelScope.launch {

        when (val result = repository.getItems()) {
            is ApiResult.Success -> {
                _itemsFlow.emit(result.data)
            }

            is ApiResult.Error -> {
                Log.d("testTest", result.message)
            }
        }
    }


}