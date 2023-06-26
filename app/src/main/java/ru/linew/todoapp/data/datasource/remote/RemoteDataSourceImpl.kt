package ru.linew.todoapp.data.datasource.remote

import android.provider.Settings
import ru.linew.todoapp.data.model.TodoItemData
import ru.linew.todoapp.data.model.toResponse
import ru.linew.todoapp.data.repository.datasource.remote.RemoteDataSource
import ru.linew.todoapp.data.network.TodoApiService
import ru.linew.todoapp.data.network.model.send.TodoItemContainer
import ru.linew.todoapp.data.network.model.toDto
import ru.linew.todoapp.data.repository.datasource.local.SharedPreferencesDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: TodoApiService,
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) :
    RemoteDataSource {
    override suspend fun provideListOfTodos(): List<TodoItemData> {
        val response = apiService.getTodoList()
        sharedPreferencesDataSource.setCurrentRevision(response.revision.toInt())
        return response.list.map { it.toDto() }
    }

    override suspend fun addTodo(revision: Int, todoItemData: TodoItemData) {
        apiService.addTodo(
            sharedPreferencesDataSource.getLocalCurrentRevision(),
            TodoItemContainer(
                todoItemData.toResponse()
                    .copy(lastUpdatedBy = sharedPreferencesDataSource.getDeviceId())
            )
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
        apiService.updateTodoById(
            sharedPreferencesDataSource.getLocalCurrentRevision(),
            itemToSend.element.id,
            itemToSend
        )
    }

    override suspend fun deleteTodoById(id: String) {
        apiService.deleteTodoById(sharedPreferencesDataSource.getLocalCurrentRevision(), id)
    }


}