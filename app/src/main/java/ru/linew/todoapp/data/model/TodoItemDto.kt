package ru.linew.todoapp.data.model

data class TodoItemDto(
    val id: String,
    val body: String,
    val importance: String,
    val deadlineTime: Long?,
    var isCompleted: Boolean,
    val creationTime: Long,
    val modificationTime: Long?
)
