package com.example.summary_4v2.repository

import com.example.summary_4v2.model.Item
import com.example.summary_4v2.network.ApiResult
import com.example.summary_4v2.network.ItemApi
import okio.IOException

class MainRepository(private val itemApi: ItemApi) {
    suspend fun getItems(): ApiResult<List<Item>> {
        return try {
            val response = itemApi.getItems()

            if (response.isSuccessful) {
                val items = response.body() ?: emptyList()
                ApiResult.Success(items)
            } else {
                ApiResult.Error("Failed to fetch items: ${response.errorBody()?.string()}")
            }
        } catch (e: IOException) {
            ApiResult.Error("Network error: ${e.message}")
        }
    }
}