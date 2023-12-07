package com.example.summary_4v2.network

import com.example.summary_4v2.model.Item
import retrofit2.Response
import retrofit2.http.GET

interface ItemApi {

    @GET("744fa574-a483-43f6-a1d7-c65c87b5d9b2")
    suspend fun getItems(): Response<List<Item>>
}

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T) : ApiResult<T>()
    data class Error(val message: String) : ApiResult<Nothing>()
}