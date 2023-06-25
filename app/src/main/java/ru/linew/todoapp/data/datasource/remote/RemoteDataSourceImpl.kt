package ru.linew.todoapp.data.datasource.remote

import ru.linew.todoapp.data.model.TodoItemDto
import ru.linew.todoapp.data.model.toResponse
import ru.linew.todoapp.data.repository.datasource.remote.RemoteDataSource
import ru.linew.todoapp.data.network.TodoApiService
import ru.linew.todoapp.data.network.model.TodoItemContainer
import ru.linew.todoapp.data.network.model.toDto
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val apiService: TodoApiService):
    RemoteDataSource {
    override suspend fun provideTodos(): List<TodoItemDto> {
        return apiService.getTodoList().list.map { it.toDto() }
    }
    override suspend fun addTodo(todoItemDto: TodoItemDto){
        try {
            apiService.addTodo(14, TodoItemContainer(todoItemDto.toResponse()))
        }
        catch (e: Exception){
            val b = 10
        }

    }
}