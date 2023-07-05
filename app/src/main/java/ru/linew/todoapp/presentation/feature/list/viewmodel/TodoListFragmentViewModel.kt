package ru.linew.todoapp.presentation.feature.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.linew.todoapp.data.model.exception.TodoSyncFailed
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.feature.list.viewmodel.state.ErrorState
import ru.linew.todoapp.presentation.feature.list.viewmodel.state.Result
import ru.linew.todoapp.presentation.model.TodoItem

class TodoListFragmentViewModel @AssistedInject constructor(
    val repository: TodoItemsRepository
) :
    ViewModel() {
    @AssistedFactory
    interface TodoListFragmentViewModelFactory {
        fun create(): TodoListFragmentViewModel
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val factory: TodoListFragmentViewModelFactory) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }

    private val _errorState = MutableLiveData<ErrorState>(ErrorState.Ok)
    val errorState: LiveData<ErrorState>
        get() = _errorState


    private val _listTodos = MutableLiveData<Result>(Result.Null)
    val listTodos: LiveData<Result>
        get() = _listTodos

    private var tempTodos: List<TodoItem> = emptyList()
    private var visibility: Boolean = true
    private var isInitialized: Boolean = false

    init {
        viewModelScope.launch {
            repository.todoListFlow.collectLatest {
                if (isInitialized) {
                    tempTodos = it
                    postListToUi()
                } else if (!isInitialized && it.isEmpty()) {
                    isInitialized = true
                }

            }
        }
    }

    private fun postListToUi() {
        _listTodos.postValue(Result.Success(tempTodos, visibility))
    }


    fun visibilityStateChanged() {
        visibility = !visibility
        postListToUi()
    }

    fun syncList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.syncLocalListOfTodo()
                _errorState.postValue(ErrorState.Ok)
            } catch (e: TodoSyncFailed) {
                _errorState.postValue(ErrorState.Error)
            }
        }
    }

    fun errorShowed() {
        _errorState.postValue(ErrorState.Ok)
    }

    fun todoCompleteStatusChanged(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todoItem)
        }
    }


}
