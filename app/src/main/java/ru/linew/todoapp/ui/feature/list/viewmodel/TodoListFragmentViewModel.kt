package ru.linew.todoapp.ui.feature.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.linew.todoapp.data.mapper.toUiLayer
import ru.linew.todoapp.data.repository.TodoItemsRepositoryImpl
import ru.linew.todoapp.ui.feature.list.interactor.TodoItemsRepository
import ru.linew.todoapp.ui.feature.list.model.TodoItem

class TodoListFragmentViewModel: ViewModel() {

    private val repository: TodoItemsRepository = TodoItemsRepositoryImpl()
    fun setupViewModelListener(){
        _todos.postValue(repository.provideListOfTodo().map { it.toUiLayer() })
        repository.dataUpdatedCallback = {
            _todos.postValue(repository.provideListOfTodo().map { it.toUiLayer() })
        }
    }
    private val _todos = MutableLiveData<List<TodoItem>>()
    val todos: LiveData<List<TodoItem>>
        get() = _todos


    val items = repository.provideListOfTodo()
}