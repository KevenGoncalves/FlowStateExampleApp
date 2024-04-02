package com.example.flowstateexample.api.services

import com.example.flowstateexample.api.models.TodoModel
import com.example.flowstateexample.api.models.TodoModelResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TodoService {

    @GET("todos")
    suspend fun getTodos(
        @Query("limit") limit: Int = 10,
    ): Response<TodoModelResponse>

    @POST("todos/add")
    suspend fun createTodo(@Body todo: TodoModel): Response<TodoModel>
}