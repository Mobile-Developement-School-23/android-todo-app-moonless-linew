package ru.linew.todoapp.data.datasource.local

import ru.linew.todoapp.data.model.TodoItemDto
import ru.linew.todoapp.data.repository.datasource.local.LocalDataSource
import ru.linew.todoapp.data.db.TodoDao
import ru.linew.todoapp.data.db.model.toDto
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val todoDao: TodoDao): LocalDataSource {
    override suspend fun provideListOfTodos(): List<TodoItemDto> {
        return todoDao.provideListOfTodos().map { it.toDto() }
    }
}