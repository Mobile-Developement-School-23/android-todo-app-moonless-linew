package ru.linew.todoapp.data.network

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.linew.todoapp.data.network.model.recieve.TodoListResponse
import ru.linew.todoapp.data.network.model.recieve.TodoSingleResponse
import ru.linew.todoapp.data.network.model.send.TodoItemContainer
import ru.linew.todoapp.data.network.model.send.TodoListContainer


interface TodoApiService {
    @GET("list")
    suspend fun getTodoList(): TodoListResponse

    @PATCH("list")
    suspend fun updateTodoList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body list: TodoListContainer
    ):TodoListResponse

    @GET("list/{id}")
    suspend fun getTodoItemById(@Path("id") trackID: String): TodoSingleResponse

    @POST("list")
    suspend fun addTodo(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body element: TodoItemContainer
    ): TodoSingleResponse

    @PUT("list/{id}")
    suspend fun updateTodoById(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body element: TodoItemContainer
    ): TodoSingleResponse

    @DELETE("list/{id}")
    suspend fun deleteTodoById(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String
    ): TodoSingleResponse

}
