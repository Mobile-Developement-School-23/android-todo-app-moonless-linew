package ru.linew.todoapp.presentation.feature.list.connection

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import ru.linew.todoapp.di.scope.ListFragmentScope
import ru.linew.todoapp.presentation.feature.list.connection.state.ConnectivityState
import javax.inject.Inject

@ListFragmentScope
class ConnectivityStateProvider @Inject constructor(
    private val connectivityManager: ConnectivityManager
) : NetworkCallback() {

    private val _connectivityState = MutableStateFlow<ConnectivityState>(ConnectivityState.Null)
    val connectivityState: StateFlow<ConnectivityState> = _connectivityState

    init {
        connectivityManager.registerDefaultNetworkCallback(this)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        _connectivityState.update { ConnectivityState.Connected }
    }

    override fun onLost(network: Network) {
        super.onLost(network)
        _connectivityState.update { ConnectivityState.Disconnected }
    }
}