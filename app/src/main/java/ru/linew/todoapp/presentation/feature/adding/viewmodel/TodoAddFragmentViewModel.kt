package ru.linew.todoapp.presentation.feature.adding.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.linew.todoapp.presentation.feature.adding.viewmodel.state.EditStatus
import ru.linew.todoapp.presentation.feature.adding.viewmodel.state.Result
import ru.linew.todoapp.presentation.model.AddFragmentMode
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.repository.TodoItemsRepository
import java.util.*

class TodoAddFragmentViewModel @AssistedInject constructor(private val repository: TodoItemsRepository) :
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

    private lateinit var mode: AddFragmentMode

    private val _currentTodo = MutableStateFlow<Result<TodoItem>>(Result.Loading)
    val currentTodo: StateFlow<Result<TodoItem>> = _currentTodo

    private val _currentEditStatus = MutableStateFlow<EditStatus>(EditStatus.Null)
    val currentEditStatus: StateFlow<EditStatus> = _currentEditStatus

    fun todoBodyTextChanged(body: String) {
        if (currentTodo.value is Result.Complete)
            (currentTodo.value as Result.Complete<TodoItem>).result.body = body
    }

    fun todoPriorityChanged(priority: Priority) {
        if (currentTodo.value is Result.Complete)
            (currentTodo.value as Result.Complete<TodoItem>).result.priority = priority
    }

    fun todoDeadlineTimeChanged(deadlineTime: Long?) {
        if (currentTodo.value is Result.Complete)
            (currentTodo.value as Result.Complete<TodoItem>).result.deadlineTime = deadlineTime
    }

    fun deleteButtonClicked(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodoById(id)
            _currentEditStatus.value = EditStatus.Done
        }
    }

    fun onCreate(id: String?) {
        if (id == null) {
            startAddingMode()
        } else {
            startUpdatingMode(id)
        }
    }

    fun saveButtonClicked() {
        if (_currentEditStatus.value != EditStatus.InProcess)
            when (mode) {
                AddFragmentMode.ADDING -> addTodo()
                AddFragmentMode.UPDATING -> updateTodo()
            }
    }

    private fun provideDefaultTodoItem() = TodoItem(
        UUID.randomUUID().toString(),
        "",
        Priority.NO,
        deadlineTime = null,
        isCompleted = false,
        creationTime = System.currentTimeMillis(),
        System.currentTimeMillis()
    )

    private fun startUpdatingMode(id: String) {
        mode = AddFragmentMode.UPDATING
        viewModelScope.launch(Dispatchers.IO) {
            _currentTodo.value = Result.Complete(repository.getTodoById(id))
        }
    }

    private fun updateTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentEditStatus.value = EditStatus.InProcess
            repository.updateTodo((currentTodo.value as Result.Complete).result)
            _currentEditStatus.value = EditStatus.Done
            _currentTodo.value = Result.Loading
        }
    }

    private fun startAddingMode() {
        mode = AddFragmentMode.ADDING
        if (_currentTodo.value is Result.Loading) {
            _currentTodo.value = Result.Complete(
                provideDefaultTodoItem()
            )
        }
    }

    private fun addTodo() {
        viewModelScope.launch(Dispatchers.IO) {
            _currentEditStatus.value = EditStatus.InProcess
            repository.addTodo((currentTodo.value as Result.Complete).result)
            _currentEditStatus.value = EditStatus.Done
            _currentTodo.value = Result.Loading
        }
    }
}
