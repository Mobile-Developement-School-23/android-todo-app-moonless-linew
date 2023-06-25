package ru.linew.todoapp.data.repository


import ru.linew.todoapp.data.model.toUi
import ru.linew.todoapp.data.repository.datasource.local.LocalDataSource
import ru.linew.todoapp.data.repository.datasource.remote.RemoteDataSource
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.model.toDto
import javax.inject.Inject


class TodoItemsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) :
    TodoItemsRepository {
    override suspend fun addTodo(item: TodoItem) {
        remoteDataSource.addTodo(item.toDto())
    }

    override suspend fun updateTodo(item: TodoItem) {
        //todos[todos.indexOfFirst { it.id == item.id }] = item.toDataLayer()
    }

    override suspend fun deleteTodoById(id: String) {
        //todos.removeIf { it.id == id }
    }

    override suspend fun getTodoById(id: String): TodoItem {
        //заглушка
        return remoteDataSource.provideTodos().single { it.id == id }.toUi()
    }

    override suspend fun provideListOfTodo(): List<TodoItem> =
        remoteDataSource.provideTodos().map { it.toUi() }

}
