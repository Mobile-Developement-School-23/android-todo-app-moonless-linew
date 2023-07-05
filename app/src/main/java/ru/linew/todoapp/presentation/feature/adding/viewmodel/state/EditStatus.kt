package ru.linew.todoapp.presentation.feature.adding.viewmodel.state

sealed class EditStatus {
    object Null : EditStatus()
    object Done : EditStatus()
    object InProcess: EditStatus()
}
