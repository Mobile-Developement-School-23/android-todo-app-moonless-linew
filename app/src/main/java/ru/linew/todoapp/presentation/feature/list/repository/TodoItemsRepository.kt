package ru.linew.todoapp.presentation.feature.list.repository

import kotlinx.coroutines.flow.MutableStateFlow
import ru.linew.todoapp.presentation.model.TodoItem

interface TodoItemsRepository {

    val todoListFlow: MutableStateFlow<List<TodoItem>>
    suspend fun addTodo(todoItem: TodoItem)
    suspend fun updateTodo(todoItem: TodoItem)
    suspend fun deleteTodoById(id: String)
    suspend fun getTodoById(id: String): TodoItem
    suspend fun syncListOfTodo()

}