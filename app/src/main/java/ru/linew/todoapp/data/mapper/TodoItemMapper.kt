package ru.linew.todoapp.data.mapper

import ru.linew.todoapp.data.model.TodoItemDto
import ru.linew.todoapp.ui.feature.list.model.Importance
import ru.linew.todoapp.ui.feature.list.model.TodoItem
import java.util.Date

fun TodoItemDto.toUiLayer(): TodoItem =
    TodoItem(
        id = id,
        body = body,
        importance = Importance.valueOf(importance),
        deadlineTime = if (deadlineTime == null) null else Date(deadlineTime),
        isCompleted = isCompleted,
        creationTime = Date(creationTime),
        modificationTime = if (modificationTime == null) null else Date(modificationTime)
    )

fun TodoItem.toDataLayer() =
    TodoItemDto(
        id = id,
        body = body,
        importance = importance.toString(),
        deadlineTime = if (deadlineTime == null) null else deadlineTime.time,
        isCompleted = isCompleted,
        creationTime = creationTime.time,
        modificationTime = if (modificationTime == null) null else modificationTime.time
    )