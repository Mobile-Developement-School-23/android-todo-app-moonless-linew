package ru.linew.todoapp.presentation.application

import android.app.Application
import android.content.Context
import ru.linew.todoapp.di.component.AppComponent
import ru.linew.todoapp.di.component.DaggerAppComponent

class TodoApp: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
    }
}
val Context.appComponent: AppComponent
    get() = when(this){
        is TodoApp -> appComponent
        else -> this.applicationContext.appComponent
    }