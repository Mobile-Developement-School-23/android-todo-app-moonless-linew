package ru.linew.todoapp.ui.feature.list.interactor

import ru.linew.todoapp.data.model.TodoItemDto

interface TodoItemsRepository {
    var dataUpdatedCallback: () -> Unit

    fun addOrUpdateTodo(item: TodoItemDto)

    fun deleteTodoById(id: String)
    fun getTodoById(id: String): TodoItemDto
    fun provideListOfTodo(): List<TodoItemDto>

}