package ru.linew.todoapp.data.repository


import ru.linew.todoapp.data.model.toUi
import ru.linew.todoapp.data.repository.datasource.local.LocalDataSource
import ru.linew.todoapp.data.repository.datasource.local.SharedPreferencesDataSource
import ru.linew.todoapp.data.repository.datasource.remote.RemoteDataSource
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.model.toDto
import java.net.UnknownHostException
import javax.inject.Inject


class TodoItemsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) :
    TodoItemsRepository {
    override suspend fun addTodo(todoItem: TodoItem) {
        try {
            remoteDataSource.addTodo(sharedPreferencesDataSource.getLocalCurrentRevision(),todoItem.toDto())
        }catch (e: UnknownHostException){
            localDataSource.addTodo(todoItem.toDto())
            sharedPreferencesDataSource.flagNeedSyncUp()
        }

    }

    override suspend fun updateTodo(todoItem: TodoItem) {
        try {
            remoteDataSource.updateTodo(todoItem.toDto())
            localDataSource.updateTodo(todoItem.toDto())
        }
        catch (e: UnknownHostException){
            localDataSource.updateTodo(todoItem.toDto())
            sharedPreferencesDataSource.flagNeedSyncUp()
        }
    }

    override suspend fun deleteTodoById(id: String) {
        try {
            remoteDataSource.deleteTodoById(id)
            localDataSource.deleteTodoById(id)
        } catch (e: UnknownHostException){
            localDataSource.deleteTodoById(id)
            sharedPreferencesDataSource.flagNeedSyncUp()
        }
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
        catch (e: UnknownHostException){
            val localTodos = localDataSource.getListOfTodos()
            sharedPreferencesDataSource.flagNeedSyncUp()
            localTodos.map { it.toUi() }
        }


}
