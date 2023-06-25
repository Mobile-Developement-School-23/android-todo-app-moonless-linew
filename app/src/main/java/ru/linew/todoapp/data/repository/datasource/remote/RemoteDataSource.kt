package ru.linew.todoapp.data.repository.datasource.remote

import ru.linew.todoapp.data.model.TodoItemDto


interface RemoteDataSource {
    suspend fun provideTodos(): List<TodoItemDto>

    suspend fun addTodo(todoItemDto: TodoItemDto)
}