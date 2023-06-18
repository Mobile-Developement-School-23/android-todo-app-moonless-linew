package ru.linew.todoapp.ui.feature.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ru.linew.todoapp.data.mapper.toUiLayer
import ru.linew.todoapp.ui.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.ui.model.TodoItem

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

    private val _todos = MutableLiveData<List<TodoItem>>()
    val todos: LiveData<List<TodoItem>>
        get() = _todos
    fun setupViewModelListener(){
        _todos.postValue(repository.provideListOfTodo().map { it.toUiLayer() })
        repository.dataUpdatedCallback = {
            _todos.postValue(repository.provideListOfTodo().map { it.toUiLayer() })
        }
    }
}