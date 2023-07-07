package ru.linew.todoapp.di.module

import android.content.Context
import android.net.ConnectivityManager
import dagger.Module
import dagger.Provides
import ru.linew.todoapp.di.scope.ListFragmentScope

@Module
object ConnectivityManagerModule {
    @ListFragmentScope
    @Provides
    fun provideConnectivityManager(context: Context): ConnectivityManager {
        return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}