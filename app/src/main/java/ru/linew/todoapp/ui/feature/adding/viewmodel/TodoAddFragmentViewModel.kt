package ru.linew.todoapp.ui.feature.adding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.linew.todoapp.data.mapper.toUiLayer
import ru.linew.todoapp.data.model.TodoItemDto
import ru.linew.todoapp.ui.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.ui.model.Priority
import ru.linew.todoapp.ui.model.TodoItem
import java.util.*

class TodoAddFragmentViewModel @AssistedInject constructor(val repository: TodoItemsRepository): ViewModel() {
    @AssistedFactory
    interface TodoAddFragmentViewModelFactory{
        fun create(): TodoAddFragmentViewModel
    }
    @Suppress("UNCHECKED_CAST")
    class Factory(private val factory: TodoAddFragmentViewModelFactory) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }

    var id: String? = null
    var body: String = ""
    var priority: Priority = Priority.NO
    var deadlineTime: Long? = null
    var currentTodo: TodoItem? = null
    fun deleteItem(id: String){
        repository.deleteTodoById(id)
    }
    fun loadItem(id: String){
        val todo = repository.getTodoById(id)?.toUiLayer()
        if(todo == null){
            this.id = null
        }
        body = todo?.body ?: ""
        priority = todo?.priority ?: Priority.NO
        deadlineTime = todo?.deadlineTime
        currentTodo = todo
    }
    fun addOrUpdateTodo(){
        var modificationTime: Long? = null
        if (id != null){
            modificationTime = System.currentTimeMillis()
        }
        repository.addOrUpdateTodo(TodoItemDto(
            id = id ?: Random().nextInt(1000).toString(),
            body = body,
            priority = priority.toString(),
            deadlineTime = deadlineTime,
            isCompleted = false,
            creationTime = System.currentTimeMillis(),
            modificationTime = modificationTime

        ))
    }
}