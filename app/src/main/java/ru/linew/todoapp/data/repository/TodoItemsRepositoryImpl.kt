package ru.linew.todoapp.data.repository


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val todoListFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())

    //syncList() - обновляет

    override suspend fun addTodo(todoItem: TodoItem) {
        try {
            remoteDataSource.addTodo(sharedPreferencesDataSource.getLocalCurrentRevision(),todoItem.toDto())
        }
        catch (e: UnknownHostException){
            sharedPreferencesDataSource.flagNeedSyncUp()
        }
        finally {
            localDataSource.addTodo(todoItem.toDto())
        }

    }

    override suspend fun updateTodo(todoItem: TodoItem) {
        try {
            remoteDataSource.updateTodo(todoItem.toDto())

        }
        catch (e: UnknownHostException){
            sharedPreferencesDataSource.flagNeedSyncUp()
            //кастмоный экспешн
        }
        finally {
            localDataSource.updateTodo(todoItem.toDto())
        }
    }

    override suspend fun deleteTodoById(id: String) {
        try {
            remoteDataSource.deleteTodoById(id)
        }
        catch (e: UnknownHostException){
            sharedPreferencesDataSource.flagNeedSyncUp()
        }
        finally {
            localDataSource.deleteTodoById(id)
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
            todoListFlow.emit(remoteTodos.map { it.toUi() })
            remoteTodos.map { it.toUi() }
        }
        catch (e: UnknownHostException){
            val localTodos = localDataSource.getListOfTodos()
            sharedPreferencesDataSource.flagNeedSyncUp()
            todoListFlow.emit(localTodos.map { it.toUi() })
            localTodos.map { it.toUi() }
        }
    }
