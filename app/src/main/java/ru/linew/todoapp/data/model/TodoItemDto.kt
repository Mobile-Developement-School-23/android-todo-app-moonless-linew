package ru.linew.todoapp.data.model

data class TodoItemDto(
    val id: String,
    val body: String,
    val importance: String,
    val deadlineTime: Long?,
    val isCompleted: Boolean,
    val creationTime: Long,
    val modificationTime: Long?
)
