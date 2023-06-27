package ru.linew.todoapp.data.model

import ru.linew.todoapp.data.db.model.TodoItemEntity
import ru.linew.todoapp.data.network.model.TodoItemResponse
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem

data class TodoItemData(
    val id: String,
    val body: String,
    val priority: String,
    val deadlineTime: Long?,
    val isCompleted: Boolean,
    val creationTime: Long,
    val modificationTime:Long
)
fun TodoItemData.toUi(): TodoItem =
    TodoItem(
        id = id,
        body = body,
        priority = Priority.valueOf(priority),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime
    )

fun TodoItemData.toResponse(): TodoItemResponse =
    TodoItemResponse(
        id = id,
        body = body,
        priority = renamePriorityToResponse(priority),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime,
        color = null,
        lastUpdatedBy = ""
    )


fun TodoItemData.toEntity(): TodoItemEntity =
    TodoItemEntity(
        id = id,
        body = body,
        priority = priority,
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime
    )

private fun renamePriorityToResponse(priority: String): String {
    return when(priority){
        "NO" -> "basic"
        "LOW" -> "low"
        "HIGH" -> "important"
        else -> throw IllegalArgumentException()
    }
}
