package ru.linew.todoapp.data.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.linew.todoapp.data.model.TodoItemData

@Entity(tableName = "todos")
data class TodoItemEntity(
    @PrimaryKey
    val id: String,
    val body: String,
    val priority: String,
    val deadlineTime: Long?,
    val isCompleted: Boolean,
    val creationTime: Long,
    val modificationTime: Long
)

fun TodoItemEntity.toDto(): TodoItemData =
    TodoItemData(
        id = id,
        body = body,
        priority = priority,
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime
    )

