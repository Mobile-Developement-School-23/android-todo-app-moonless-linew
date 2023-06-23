package ru.linew.todoapp.data.repository.datasource

import ru.linew.todoapp.data.retrofit.model.TodoItemResponse


interface TodoRemoteDataSource {
    suspend fun provideTodos(): List<TodoItemResponse>
}