package ru.linew.todoapp.data.repository


import ru.linew.todoapp.data.repository.datasource.TodoRemoteDataSource
import ru.linew.todoapp.data.retrofit.model.toUiLayer
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.model.TodoItem
import javax.inject.Inject


class TodoItemsRepositoryImpl @Inject constructor(private val todoRemoteDataSource: TodoRemoteDataSource) :
    TodoItemsRepository {
    override suspend fun addTodo(item: TodoItem) {
        //todos.add(item.toDataLayer())

    }

    override suspend fun updateTodo(item: TodoItem) {
        //todos[todos.indexOfFirst { it.id == item.id }] = item.toDataLayer()
    }

    override suspend fun deleteTodoById(id: String) {
        //todos.removeIf { it.id == id }
    }

    override suspend fun getTodoById(id: String): TodoItem {
        //заглушка
        return todoRemoteDataSource.provideTodos().single { it.id == id }.toUiLayer()
        }
    override suspend fun provideListOfTodo(): List<TodoItem> = todoRemoteDataSource.provideTodos().map { it.toUiLayer() }

}
