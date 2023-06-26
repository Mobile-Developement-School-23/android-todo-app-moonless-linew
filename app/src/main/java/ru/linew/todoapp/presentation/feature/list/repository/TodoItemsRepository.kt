package ru.linew.todoapp.presentation.feature.list.repository

import ru.linew.todoapp.presentation.model.TodoItem

interface TodoItemsRepository {
    suspend fun addTodo(todoItem: TodoItem)
    suspend fun updateTodo(todoItem: TodoItem)
    suspend fun deleteTodoById(id: String)
    suspend fun getTodoById(id: String): TodoItem
    suspend fun getListOfTodo(): List<TodoItem>

}