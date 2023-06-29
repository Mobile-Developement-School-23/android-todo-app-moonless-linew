package ru.linew.todoapp.data.datasource.remote

import ru.linew.todoapp.data.model.TodoItemData
import ru.linew.todoapp.data.model.toResponse
import ru.linew.todoapp.data.network.TodoApiService
import ru.linew.todoapp.data.network.model.send.TodoItemContainer
import ru.linew.todoapp.data.network.model.send.TodoListContainer
import ru.linew.todoapp.data.network.model.toData
import ru.linew.todoapp.data.repository.datasource.local.SharedPreferencesDataSource
import ru.linew.todoapp.data.repository.datasource.remote.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: TodoApiService,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) :
    RemoteDataSource {
    override suspend fun provideListOfTodos(): List<TodoItemData> {
        val response = apiService.getTodoList()
        sharedPreferencesDataSource.setCurrentRevision(response.revision.toInt())
        return response.list.map { it.toData() }
    }

    override suspend fun addTodo(revision: Int, todoItemData: TodoItemData) {
        val currentRevision = apiService.addTodo(
            sharedPreferencesDataSource.getLocalCurrentRevision(),
            TodoItemContainer(
                todoItemData.toResponse()
                    .copy(lastUpdatedBy = sharedPreferencesDataSource.getDeviceId())
            )
        ).revision
        sharedPreferencesDataSource.setCurrentRevision(
            currentRevision.toInt()
        )
    }

    override suspend fun getRemoteCurrentRevision(): Int {
        return apiService.getTodoList().revision.toInt()
    }

    override suspend fun updateTodo(todoItemData: TodoItemData) {
        val itemToSend = TodoItemContainer(
            todoItemData.toResponse()
                .copy(lastUpdatedBy = sharedPreferencesDataSource.getDeviceId())
        )
        val currentRevision = apiService.updateTodoById(
            sharedPreferencesDataSource.getLocalCurrentRevision(),
            itemToSend.element.id,
            itemToSend
        ).revision.toInt()
        sharedPreferencesDataSource.setCurrentRevision(
            currentRevision
        )
    }

    override suspend fun deleteTodoById(id: String) {
        val currentRevision = apiService.deleteTodoById(
            sharedPreferencesDataSource.getLocalCurrentRevision(),
            id
        ).revision
        sharedPreferencesDataSource.setCurrentRevision(currentRevision.toInt())
    }

    override suspend fun forceUpdateListOfTodos(todos: List<TodoItemData>) {
        val currentRevision = apiService.updateTodoList(
            sharedPreferencesDataSource.getLocalCurrentRevision(),
            TodoListContainer(todos.map { it.toResponse() })
        ).revision
        sharedPreferencesDataSource.setCurrentRevision(currentRevision.toInt())
    }


}