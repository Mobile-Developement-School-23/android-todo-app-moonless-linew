package ru.linew.todoapp.data.retrofit

import retrofit2.http.GET
import retrofit2.http.Headers
import ru.linew.todoapp.data.retrofit.model.TodoListResponse


interface TodoApiService{
    @GET("list")
    @Headers("Authorization: Bearer amygdaline")
    suspend fun getTodoList(): TodoListResponse
}