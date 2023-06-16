package ru.linew.todoapp.data.mapper

import ru.linew.todoapp.data.model.TodoItemDto
import ru.linew.todoapp.ui.feature.list.model.Priority
import ru.linew.todoapp.ui.feature.list.model.TodoItem

fun TodoItemDto.toUiLayer(): TodoItem =
    TodoItem(
        id = id,
        body = body,
        priority = Priority.valueOf(priority),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime
    )

fun TodoItem.toDataLayer() =
    TodoItemDto(
        id = id,
        body = body,
        priority = priority.toString(),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime
    )
