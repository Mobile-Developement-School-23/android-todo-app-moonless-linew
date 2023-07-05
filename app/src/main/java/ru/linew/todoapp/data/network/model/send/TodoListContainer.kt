package ru.linew.todoapp.data.network.model.send

import ru.linew.todoapp.data.network.model.TodoItemResponse

data class TodoListContainer (
    val list: List<TodoItemResponse>
    )
