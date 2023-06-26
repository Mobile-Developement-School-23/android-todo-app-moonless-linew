package ru.linew.todoapp.data.repository


import ru.linew.todoapp.data.model.toUi
import ru.linew.todoapp.data.repository.datasource.local.LocalDataSource
import ru.linew.todoapp.data.repository.datasource.local.SharedPreferencesDataSource
import ru.linew.todoapp.data.repository.datasource.remote.RemoteDataSource
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.model.toDto
import javax.inject.Inject


class TodoItemsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) :
    TodoItemsRepository {
    override suspend fun addTodo(item: TodoItem) {
        remoteDataSource.addTodo(sharedPreferencesDataSource.getLocalCurrentRevision(),item.toDto())
        sharedPreferencesDataSource.incrementCurrentRevision()
    }

    override suspend fun updateTodo(item: TodoItem) {
        //todos[todos.indexOfFirst { it.id == item.id }] = item.toDataLayer()
    }

    override suspend fun deleteTodoById(id: String) {
        //todos.removeIf { it.id == id }
    }

    override suspend fun getTodoById(id: String): TodoItem {
        return localDataSource.getTodoById(id).toUi()
    }

    override suspend fun getListOfTodo(): List<TodoItem> =
        try {
            val remoteTodos = remoteDataSource.provideListOfTodos()
            localDataSource.addListOfTodos(remoteTodos)
            sharedPreferencesDataSource.flagNeedSyncDown()
            remoteTodos.map { it.toUi() }
        }
        catch (e: Exception){
            val localTodos = localDataSource.getListOfTodos()
            sharedPreferencesDataSource.flagNeedSyncUp()
            localTodos.map { it.toUi() }
        }


}
