package ru.linew.todoapp.presentation.feature.adding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.model.Mode
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem
import java.util.*

class TodoAddFragmentViewModel @AssistedInject constructor(val repository: TodoItemsRepository) :
    ViewModel() {
    @AssistedFactory
    interface TodoAddFragmentViewModelFactory {
        fun create(): TodoAddFragmentViewModel
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val factory: TodoAddFragmentViewModelFactory) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }

    private lateinit var mode: Mode
    private var _currentTodo: TodoItem? = null
    val currentTodo: TodoItem
        get() = _currentTodo!!

    fun deleteItemClicked(id: String) {
        viewModelScope.launch {
            repository.deleteTodoById(id)
        }
    }

    fun onCreate(id: String?) {
        if (id == null) {
            mode = Mode.CREATING
            if (_currentTodo == null) {
                _currentTodo = TodoItem(
                    UUID.randomUUID().toString(),
                    "",
                    Priority.NO,
                    deadlineTime = null,
                    isCompleted = false,
                    creationTime = System.currentTimeMillis(),
                    System.currentTimeMillis()
                )
            }
        } else {
            mode = Mode.EDITING
            viewModelScope.launch {
                _currentTodo = repository.getTodoById(id)
            }
        }
    }

    fun addOrUpdateTodo() {
        when (mode) {
            Mode.CREATING -> viewModelScope.launch {
                repository.addTodo(currentTodo)
                _currentTodo = null
            }

            Mode.EDITING -> viewModelScope.launch {
                repository.updateTodo(currentTodo)
                _currentTodo = null
            }
        }
    }
}