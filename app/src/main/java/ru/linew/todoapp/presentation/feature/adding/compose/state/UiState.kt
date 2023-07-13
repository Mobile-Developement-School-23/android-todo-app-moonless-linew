package ru.linew.todoapp.presentation.feature.adding.compose.state

data class UiStateModel(
    val todoBodyText: String,

    val todoPriority: String,

    val isDeadlineSwitchChecked: Boolean,
    val deadline: Long?,
)
