package ru.linew.todoapp.data.repository.datasource.remote

import ru.linew.todoapp.data.model.TodoItemData


interface RemoteDataSource {
    suspend fun provideListOfTodos(): List<TodoItemData>

    suspend fun addTodo(revision:Int, todoItemData: TodoItemData)

    suspend fun getRemoteCurrentRevision(): Int

    suspend fun updateTodo(todoItemData: TodoItemData)

    suspend fun deleteTodoById(id: String)

    suspend fun forceUpdateListOfTodos(todos: List<TodoItemData>)
}
