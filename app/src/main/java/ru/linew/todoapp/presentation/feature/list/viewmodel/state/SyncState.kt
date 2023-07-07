package ru.linew.todoapp.presentation.feature.list.viewmodel.state

sealed class SyncState{
    object Ok: SyncState()
    object Error: SyncState()
}
