package ru.linew.todoapp.ui.feature.list.interactor

import ru.linew.todoapp.data.model.TodoItemDto

interface TodoItemsRepository {
    fun addTodo(item: TodoItemDto)
    fun deleteTodo(item: TodoItemDto)
    fun provideListOfTodo(): List<TodoItemDto>

    fun markTodoAsCompleted(item: TodoItemDto)
}