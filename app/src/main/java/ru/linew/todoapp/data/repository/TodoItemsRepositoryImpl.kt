package ru.linew.todoapp.data.repository

import ru.linew.todoapp.data.model.TodoItemDto
import ru.linew.todoapp.data.repository.datasource.TodoDataSource
import ru.linew.todoapp.ui.feature.list.repository.TodoItemsRepository
import javax.inject.Inject


class TodoItemsRepositoryImpl @Inject constructor(private val todoDataSource: TodoDataSource): TodoItemsRepository {
    //hardcode
    private val todos = todoDataSource.provideTodos().toMutableList()

    override var dataUpdatedCallback: () -> Unit = {}
    override fun addOrUpdateTodo(item: TodoItemDto) {
        try {
            todos[todos.indexOfFirst { it.id == item.id }] = item
        }
        catch(e:Exception) {
            todos.add(item)
        }
        dataUpdatedCallback()
    }


    override fun deleteTodoById(id: String) {
        todos.removeIf { it.id == id }
        dataUpdatedCallback()
    }

    override fun getTodoById(id: String): TodoItemDto? {
        return try{
            todos.single{it.id == id}
        } catch (e: Exception){
            null
        }
    }


    override fun provideListOfTodo(): List<TodoItemDto> {
        return todos

    }


}