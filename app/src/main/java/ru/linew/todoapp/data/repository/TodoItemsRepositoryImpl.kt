package ru.linew.todoapp.data.repository

import ru.linew.todoapp.data.model.TodoItemDto
import ru.linew.todoapp.data.shared.TestItems
import ru.linew.todoapp.ui.feature.list.interactor.TodoItemsRepository


class TodoItemsRepositoryImpl: TodoItemsRepository {
    //хардкод
    val todos = TestItems.items
    override fun addTodo(item: TodoItemDto) {
        todos.add(item)
    }

    override fun deleteTodo(item: TodoItemDto) {
        todos.remove(item)
    }

    override fun provideListOfTodo(): List<TodoItemDto> {
        return todos
    }

}