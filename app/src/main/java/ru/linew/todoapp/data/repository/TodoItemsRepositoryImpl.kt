package ru.linew.todoapp.data.repository

import ru.linew.todoapp.data.mapper.toDataLayer
import ru.linew.todoapp.data.mapper.toUiLayer
import ru.linew.todoapp.data.repository.datasource.TodoDataSource
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.model.TodoItem
import javax.inject.Inject


class TodoItemsRepositoryImpl @Inject constructor(todoDataSource: TodoDataSource) :
    TodoItemsRepository {
    //hardcode
    private val todos = todoDataSource.provideTodos().toMutableList()
    override var dataUpdatedCallback: () -> Unit = {}
    override fun addTodo(item: TodoItem) {
        todos.add(item.toDataLayer())
        dataUpdatedCallback()
    }

    override fun updateTodo(item: TodoItem) {
        todos[todos.indexOfFirst { it.id == item.id }] = item.toDataLayer()
        dataUpdatedCallback()
    }

    override fun deleteTodoById(id: String) {
        todos.removeIf { it.id == id }
        dataUpdatedCallback()
    }

    override fun getTodoById(id: String): TodoItem =
        todos.single { it.id == id }.toUiLayer()

    override fun provideListOfTodo(): List<TodoItem> = todos.map { it.toUiLayer() }

}
