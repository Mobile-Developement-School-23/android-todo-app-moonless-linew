package ru.linew.todoapp.presentation.model

import ru.linew.todoapp.data.model.TodoItemData


data class TodoItem(
    var id: String,
    var body: String,
    var priority: Priority,
    var deadlineTime: Long?,
    var isCompleted: Boolean,
    var creationTime: Long,
    var modificationTime:Long
)

fun TodoItem.toDto(): TodoItemData{
    return TodoItemData(
        id = id,
        body = body,
        priority = priority.toString(),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime,
    )
}
