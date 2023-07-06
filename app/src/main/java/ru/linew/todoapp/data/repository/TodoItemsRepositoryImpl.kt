package ru.linew.todoapp.data.repository


import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.linew.todoapp.data.model.exception.TodoSyncFailed
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

    private val _todoListFlow: MutableStateFlow<List<TodoItem>> = MutableStateFlow(emptyList())
    override val todoListFlow: StateFlow<List<TodoItem>>
        get() = _todoListFlow


    override suspend fun addTodo(todoItem: TodoItem) {
        try {
            remoteDataSource.addTodo(
                sharedPreferencesDataSource.getLocalCurrentRevision(),
                todoItem.toDto()
            )
        } catch (e: Exception) {
            sharedPreferencesDataSource.flagNeedSyncUp()
            //throw TodoSyncFailed()
        } finally {
            localDataSource.addTodo(todoItem.toDto())
        }

    }

    override suspend fun updateTodo(todoItem: TodoItem) {
        localDataSource.updateTodo(todoItem.toDto())
        syncFlowList()
        try {
            remoteDataSource.updateTodo(todoItem.toDto())

        } catch (e: Exception) {
            sharedPreferencesDataSource.flagNeedSyncUp()
            //throw TodoSyncFailed()
        }
    }

    override suspend fun deleteTodoById(id: String) {
        try {
            remoteDataSource.deleteTodoById(id)
        } catch (e: Exception) {
            sharedPreferencesDataSource.flagNeedSyncUp()
            //throw TodoSyncFailed()
        } finally {
            localDataSource.deleteTodoById(id)

        }
    }

    override suspend fun getTodoById(id: String): TodoItem {
        return localDataSource.getTodoById(id).toUi()
    }

    override suspend fun syncFlowList() {
        val localTodos = localDataSource.getListOfTodos()
        _todoListFlow.emit(localTodos.map { it.toUi() })
    }


    override suspend fun syncLocalListOfTodo() {
        var localTodos = localDataSource.getListOfTodos()
        try {
            val remoteTodos = remoteDataSource.provideListOfTodos()
            if (sharedPreferencesDataSource.getFlagNeedSyncState()) {
                remoteDataSource.forceUpdateListOfTodos(localTodos)
                sharedPreferencesDataSource.flagNeedSyncDown()
            } else {
                localDataSource.forceUpdateListOfTodos(remoteTodos)
                localTodos = remoteTodos
            }

        } catch (e: Exception) {
            sharedPreferencesDataSource.flagNeedSyncUp()
            throw TodoSyncFailed()
        } finally {
            _todoListFlow.emit(localTodos.map { it.toUi() })
        }
    }

}
