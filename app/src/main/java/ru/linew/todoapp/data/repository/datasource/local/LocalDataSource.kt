package ru.linew.todoapp.data.repository.datasource.local

import ru.linew.todoapp.data.model.TodoItemDto

interface LocalDataSource {
    suspend fun provideListOfTodos(): List<TodoItemDto>
}