package ru.linew.todoapp.data.repository

import ru.linew.todoapp.data.mapper.TestItems
import ru.linew.todoapp.data.mapper.toDataLayer
import ru.linew.todoapp.data.model.TodoItemDto
import ru.linew.todoapp.ui.feature.list.model.TodoItem


class TodoItemsRepositoryImpl {
    //хардкод
    val todos = TestItems.items
    fun addTodo(todoItem: TodoItem){
        todos.add(todoItem.toDataLayer())
    }
}