package ru.linew.todoapp.ui.feature.list.model


data class TodoItem(
    val id: String,
    val body: String,
    val priority: Priority,
    val deadlineTime: Long?,
    val isCompleted: Boolean,
    val creationTime: Long,
    val modificationTime:Long?
)