package ru.linew.todoapp.ui.feature.list.model

import java.util.Date


data class TodoItem(
    val id: String,
    val body: String,
    val importance: Importance,
    val deadlineTime: Date?,
    val isCompleted: Boolean,
    val creationTime: Date,
    val modificationTime:Date?
)