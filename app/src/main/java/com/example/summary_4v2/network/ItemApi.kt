package com.example.summary_4v2.network

import com.example.summary_4v2.model.Item
import retrofit2.Response
import retrofit2.http.GET

interface ItemApi {

    @GET("e27fe855-34ab-44a2-926d-9147b8f79682")
    suspend fun getItems(): Response<List<Item>>
}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}