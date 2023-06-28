package ru.linew.todoapp.presentation.feature.adding.viewmodel.state

sealed class EditStatus {
    object Deleted : EditStatus()
    object Updated : EditStatus()
    object Created: EditStatus()
    object InProcess: EditStatus()
}