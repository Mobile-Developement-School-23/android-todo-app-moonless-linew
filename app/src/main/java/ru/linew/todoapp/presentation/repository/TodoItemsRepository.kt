package ru.linew.todoapp.presentation.repository

import kotlinx.coroutines.flow.StateFlow
import ru.linew.todoapp.presentation.model.TodoItem

interface TodoItemsRepository {

    val todoListFlow: StateFlow<List<TodoItem>>
    suspend fun addTodo(todoItem: TodoItem)
    suspend fun updateTodo(todoItem: TodoItem)
    suspend fun deleteTodoById(id: String)
    suspend fun getTodoById(id: String): TodoItem

    suspend fun syncFlowList()
    suspend fun syncLocalListOfTodo()

}
