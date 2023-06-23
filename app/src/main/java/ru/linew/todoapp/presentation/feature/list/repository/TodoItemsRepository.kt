package ru.linew.todoapp.presentation.feature.list.repository

import ru.linew.todoapp.presentation.model.TodoItem

interface TodoItemsRepository {
    var dataUpdatedCallback: () -> Unit
    fun addTodo(item: TodoItem)
    fun updateTodo(item: TodoItem)
    fun deleteTodoById(id: String)
    fun getTodoById(id: String): TodoItem
    fun provideListOfTodo(): List<TodoItem>

}