package ru.linew.todoapp.presentation.feature.list.repository

import ru.linew.todoapp.presentation.model.TodoItem

interface TodoItemsRepository {
    suspend fun addTodo(item: TodoItem)
    suspend fun updateTodo(item: TodoItem)
    suspend fun deleteTodoById(id: String)
    suspend fun getTodoById(id: String): TodoItem
    suspend fun getListOfTodo(): List<TodoItem>

}