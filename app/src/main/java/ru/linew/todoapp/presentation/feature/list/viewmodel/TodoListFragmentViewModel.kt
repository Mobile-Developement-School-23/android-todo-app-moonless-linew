package ru.linew.todoapp.presentation.feature.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import ru.linew.todoapp.data.model.exception.TodoSyncFailed
import ru.linew.todoapp.presentation.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.presentation.feature.list.viewmodel.state.ErrorState
import ru.linew.todoapp.presentation.feature.list.viewmodel.state.Result
import ru.linew.todoapp.presentation.model.TodoItem

class TodoListFragmentViewModel @AssistedInject constructor(val repository: TodoItemsRepository): ViewModel() {
    @AssistedFactory
    interface TodoListFragmentViewModelFactory{
        fun create(): TodoListFragmentViewModel
    }
    @Suppress("UNCHECKED_CAST")
    class Factory(private val factory: TodoListFragmentViewModelFactory) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return factory.create() as T
        }
    }
    private val _errorState = MutableLiveData<ErrorState>(ErrorState.Ok)
    val errorState: LiveData<ErrorState>
        get() = _errorState

    private val _todos = MutableLiveData<Result>(Result.Null)
    val todos: LiveData<Result>
        get() = _todos
    fun syncList(){
        viewModelScope.launch {
            try {
                repository.syncListOfTodo()
                _errorState.postValue(ErrorState.Ok)
            }catch (e: TodoSyncFailed){
                _errorState.postValue(ErrorState.Error)
            }finally {
                repository.todoListFlow.collect{
                    _todos.postValue(Result.Success(it))

                }
            }


        }
    }

    fun errorShowed(){
        _errorState.postValue(ErrorState.Ok)
    }

    fun todoCompleteStatusChanged(todoItem: TodoItem){
        viewModelScope.launch {
            repository.updateTodo(todoItem)
        }
    }



}