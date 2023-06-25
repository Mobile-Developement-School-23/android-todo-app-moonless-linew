package ru.linew.todoapp.data.model

import ru.linew.todoapp.data.network.model.TodoItemResponse
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem

data class TodoItemDto(
    val id: String,
    val body: String,
    val priority: String,
    val deadlineTime: Long?,
    val isCompleted: Boolean,
    val creationTime: Long,
    val modificationTime:Long
)
fun TodoItemDto.toUi(): TodoItem =
    TodoItem(
        id = id,
        body = body,
        priority = Priority.valueOf(priority),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime
    )

fun TodoItemDto.toResponse(): TodoItemResponse =
    TodoItemResponse(
        id = id,
        body = body,
        priority = renamePriorityToResponse(priority),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime,
        color = null,
        lastUpdatedBy = "id1"
    )
//заглушка

private fun renamePriorityToResponse(priority: String): String {
    return when(priority){
        "NO" -> "basic"
        "LOW" -> "low"
        "HIGH" -> "important"
        else -> throw IllegalArgumentException()
    }
}
