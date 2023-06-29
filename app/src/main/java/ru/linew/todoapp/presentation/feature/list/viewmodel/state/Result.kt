package ru.linew.todoapp.presentation.feature.list.viewmodel.state

import ru.linew.todoapp.presentation.model.TodoItem

sealed class Result{
    class Success(val result: List<TodoItem>): Result()
    object Null: Result()
}
