package ru.linew.todoapp.presentation.feature.adding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.model.Mode
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem
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
    private lateinit var mode: Mode
    private lateinit var _currentTodo: TodoItem
    val currentTodo: TodoItem
    get() = _currentTodo

    fun deleteItemClicked(id: String){
        repository.deleteTodoById(id)
    }
    fun onCreate(id: String?){
        _currentTodo = if (id == null){
            mode = Mode.CREATING
            TodoItem(
                UUID.randomUUID().toString(),
                "",
                Priority.NO,
                deadlineTime = null,
                isCompleted = false,
                creationTime = System.currentTimeMillis(),
                null
            )
        } else{
            mode = Mode.EDITING
            repository.getTodoById(id)
        }
    }
    fun addOrUpdateTodo(){
        when(mode){
            Mode.CREATING -> repository.addTodo(_currentTodo)
            Mode.EDITING -> repository.updateTodo(_currentTodo)
        }
    }
}