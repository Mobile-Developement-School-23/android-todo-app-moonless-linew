package ru.linew.todoapp.presentation.feature.list.connection.state

sealed class ConnectivityState{
    object Null: ConnectivityState()
    object Connected: ConnectivityState()
    object Disconnected: ConnectivityState()
}
