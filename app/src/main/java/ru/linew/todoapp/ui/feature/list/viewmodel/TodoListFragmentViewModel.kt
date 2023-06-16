package ru.linew.todoapp.ui.feature.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.linew.todoapp.data.mapper.toUiLayer
import ru.linew.todoapp.data.repository.TodoItemsRepositoryImpl
import ru.linew.todoapp.ui.feature.list.repository.TodoItemsRepository
import ru.linew.todoapp.ui.model.TodoItem

class TodoListFragmentViewModel: ViewModel() {
    private val _todos = MutableLiveData<List<TodoItem>>()
    val todos: LiveData<List<TodoItem>>
        get() = _todos
    private val repository: TodoItemsRepository = TodoItemsRepositoryImpl
    fun setupViewModelListener(){
        _todos.postValue(repository.provideListOfTodo().map { it.toUiLayer() })
        repository.dataUpdatedCallback = {
            _todos.postValue(repository.provideListOfTodo().map { it.toUiLayer() })
        }
    }
}