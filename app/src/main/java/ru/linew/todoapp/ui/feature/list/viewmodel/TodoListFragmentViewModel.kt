package ru.linew.todoapp.ui.feature.list.viewmodel

import androidx.lifecycle.ViewModel
import ru.linew.todoapp.data.repository.TodoItemsRepositoryImpl
import ru.linew.todoapp.ui.feature.list.interactor.TodoItemsRepository

class TodoListFragmentViewModel: ViewModel() {
    val repository: TodoItemsRepository = TodoItemsRepositoryImpl()
    //с флоу работал первый раз
    val itemsFlow = repository.provideFlowListOfTodo()
}