package ru.linew.todoapp.data.datasource.remote

import ru.linew.todoapp.data.repository.datasource.TodoRemoteDataSource
import ru.linew.todoapp.data.retrofit.TodoApiService
import ru.linew.todoapp.data.retrofit.model.TodoItemResponse
import javax.inject.Inject

class TodoRemoteDataSourceImpl @Inject constructor(private val apiService: TodoApiService): TodoRemoteDataSource {
    override suspend fun provideTodos(): List<TodoItemResponse> {
        return apiService.getTodoList().list
    }
}