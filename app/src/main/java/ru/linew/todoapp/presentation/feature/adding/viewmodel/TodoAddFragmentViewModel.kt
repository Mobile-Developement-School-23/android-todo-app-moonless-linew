package ru.linew.todoapp.presentation.feature.adding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.model.Mode
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.feature.adding.viewmodel.state.Result
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

    private val _currentTodo = MutableStateFlow<Result<TodoItem>>(Result.Loading)
    val currentTodo: StateFlow<Result<TodoItem>> = _currentTodo

    fun todoBodyTextChanged(body: String){
        if(currentTodo.value is Result.Complete)
            (currentTodo.value as Result.Complete<TodoItem>).result.body = body
    }
    fun todoPriorityChanged(priority: Priority){
        if(currentTodo.value is Result.Complete)
            (currentTodo.value as Result.Complete<TodoItem>).result.priority = priority
    }

    fun todoDeadlineTimeChanged(deadlineTime: Long?){
        if(currentTodo.value is Result.Complete)
            (currentTodo.value as Result.Complete<TodoItem>).result.deadlineTime = deadlineTime
    }

    fun deleteItemClicked(id: String) {
        viewModelScope.launch {
            repository.deleteTodoById(id)
        }
    }

    fun onCreate(id: String?) {
        if (id == null) {
            mode = Mode.CREATING
            if (_currentTodo.value is Result.Loading) {
                _currentTodo.value = Result.Complete(TodoItem(
                    UUID.randomUUID().toString(),
                    "",
                    Priority.NO,
                    deadlineTime = null,
                    isCompleted = false,
                    creationTime = System.currentTimeMillis(),
                    System.currentTimeMillis()
                ))
            }
        } else {
            mode = Mode.EDITING
            viewModelScope.launch {
                _currentTodo.value = Result.Complete(repository.getTodoById(id))

            }
        }
    }

    fun addOrUpdateTodo() {
        when (mode) {
            Mode.CREATING -> viewModelScope.launch {
                repository.addTodo((currentTodo.value as Result.Complete).result)
                _currentTodo.value = Result.Loading
            }

            Mode.EDITING -> viewModelScope.launch {
                repository.updateTodo((currentTodo.value as Result.Complete).result)
                _currentTodo.value = Result.Loading
            }
        }
    }
}