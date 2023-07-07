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
import ru.linew.todoapp.presentation.feature.list.connection.ConnectivityStateProvider
import ru.linew.todoapp.presentation.feature.list.connection.state.ConnectivityState
import ru.linew.todoapp.presentation.feature.list.viewmodel.state.Result
import ru.linew.todoapp.presentation.feature.list.viewmodel.state.SyncState
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.repository.TodoItemsRepository

class TodoListFragmentViewModel @AssistedInject constructor(
    private val repository: TodoItemsRepository,
    private val connectivityStateProvider: ConnectivityStateProvider
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

    private val _syncErrorState = MutableLiveData<SyncState>(SyncState.Ok)
    val syncErrorState: LiveData<SyncState>
        get() = _syncErrorState


    private val _listTodos = MutableLiveData<Result>(Result.Null)
    val listTodos: LiveData<Result>
        get() = _listTodos

    private var tempTodos: List<TodoItem> = emptyList()
    private var visibility: Boolean = true
    private var isListInitialized: Boolean = false
    private var isNetworkAvailable = true

    init {
        setupTodoListListener()
        setupConnectivityStateListener()
    }

    private fun setupTodoListListener() = viewModelScope.launch {
        repository.todoListFlow.collectLatest {
            if (isListInitialized) {
                tempTodos = it
                postListToUi()
            } else if (it.isEmpty()) {
                isListInitialized = true
            }

        }
    }

    private fun setupConnectivityStateListener() = viewModelScope.launch {
        connectivityStateProvider.connectivityState.collectLatest {
            when(it){
                ConnectivityState.Connected -> if (!isNetworkAvailable) syncList()
                ConnectivityState.Disconnected -> isNetworkAvailable = false
                ConnectivityState.Null -> {}
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
        viewModelScope.launch() {
            syncListInternal()
        }
    }

    private suspend fun syncListInternal() {
        try {
            repository.syncLocalListOfTodo()
            _syncErrorState.postValue(SyncState.Ok)
        } catch (e: TodoSyncFailed) {
            _syncErrorState.postValue(SyncState.Error)
        }
    }

    fun errorShowed() {
        _syncErrorState.postValue(SyncState.Ok)
    }

    fun todoCompleteStatusChanged(todoItem: TodoItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todoItem)
        }
    }


}
