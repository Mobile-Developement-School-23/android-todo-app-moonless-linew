package ru.linew.todoapp.data.repository.datasource.remote

import ru.linew.todoapp.data.model.TodoItemData


interface RemoteDataSource {
    suspend fun provideListOfTodos(): List<TodoItemData>

    suspend fun addTodo(revision:Int, todoItemData: TodoItemData)

    suspend fun getRemoteCurrentRevision(): Int
}