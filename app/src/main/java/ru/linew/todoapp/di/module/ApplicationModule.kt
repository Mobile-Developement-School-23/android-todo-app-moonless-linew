package ru.linew.todoapp.di.module

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import ru.linew.todoapp.shared.Constants
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
}