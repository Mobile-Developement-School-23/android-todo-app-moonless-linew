package ru.linew.todoapp.presentation.feature.list.viewmodel.state

sealed class ErrorState{
    object Ok: ErrorState()
    object Error: ErrorState()
}
