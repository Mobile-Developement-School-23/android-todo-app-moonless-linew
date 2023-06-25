package ru.linew.todoapp.data.network

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import ru.linew.todoapp.data.network.model.TodoItemContainer
import ru.linew.todoapp.data.network.model.TodoItemResponse
import ru.linew.todoapp.data.network.model.TodoListResponse


interface TodoApiService{
    @GET("list")
    suspend fun getTodoList(): TodoListResponse

    @POST("list")
    suspend fun addTodo(@Header("X-Last-Known-Revision") revision: Int, @Body element: TodoItemContainer)

    @PATCH("list")
    suspend fun updateTodoList()
}