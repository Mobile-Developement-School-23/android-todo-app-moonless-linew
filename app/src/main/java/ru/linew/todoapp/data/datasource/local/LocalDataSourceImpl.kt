package ru.linew.todoapp.data.datasource.local

import ru.linew.todoapp.data.db.TodoDao
import ru.linew.todoapp.data.db.model.toDto
import ru.linew.todoapp.data.model.TodoItemData
import ru.linew.todoapp.data.model.toEntity
import ru.linew.todoapp.data.repository.datasource.local.LocalDataSource
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(private val todoDao: TodoDao) : LocalDataSource {
    override suspend fun getListOfTodos(): List<TodoItemData> {
        return todoDao.getListOfTodos().map { it.toDto() }
    }

    override suspend fun getTodoById(id: String): TodoItemData {
        return todoDao.getTodoById(id).toDto()
    }


    override suspend fun addTodo(todoItem: TodoItemData) {
        todoDao.addTodo(todoItem.toEntity())
    }

    override suspend fun addListOfTodos(todoItemList: List<TodoItemData>) {
        todoDao.addListOfTodos(todoItemList.map { it.toEntity() })
    }

    override suspend fun deleteTodoById(id: String) {
        todoDao.deleteTodoById(id)

    }

    override suspend fun forceUpdateListOfTodos(todoItemList: List<TodoItemData>) {
        todoDao.deleteAllTodos()
        todoDao.addListOfTodos(todoItemList.map { it.toEntity() })
    }

    override suspend fun updateTodo(todoItem: TodoItemData) {
        todoDao.updateTodo(todoItem.toEntity().copy(modificationTime = System.currentTimeMillis()))
    }
}
