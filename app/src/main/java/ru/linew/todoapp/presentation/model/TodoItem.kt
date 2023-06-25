package ru.linew.todoapp.presentation.model

import ru.linew.todoapp.data.model.TodoItemDto


data class TodoItem(
    var id: String,
    var body: String,
    var priority: Priority,
    var deadlineTime: Long?,
    var isCompleted: Boolean,
    var creationTime: Long,
    var modificationTime:Long
)

fun TodoItem.toDto(): TodoItemDto{
    return TodoItemDto(
        id = id,
        body = body,
        priority = priority.toString(),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime,
    )
}