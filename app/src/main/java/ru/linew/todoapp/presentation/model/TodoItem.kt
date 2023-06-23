package ru.linew.todoapp.presentation.model


data class TodoItem(
    var id: String,
    var body: String,
    var priority: Priority,
    var deadlineTime: Long?,
    var isCompleted: Boolean,
    var creationTime: Long,
    var modificationTime:Long?
)