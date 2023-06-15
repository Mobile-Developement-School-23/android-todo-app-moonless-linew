package ru.linew.todoapp.ui.feature.list.interactor

import kotlinx.coroutines.flow.Flow
import ru.linew.todoapp.data.model.TodoItemDto

interface TodoItemsRepository {
    fun addTodo(item: TodoItemDto)
    fun deleteTodoByObject(item: TodoItemDto)

    fun deleteTodoById(id: String)
    fun provideListOfTodo(): List<TodoItemDto>

    fun provideFlowListOfTodo(): Flow<List<TodoItemDto>>
    fun markTodoAsCompleted(item: TodoItemDto)
}