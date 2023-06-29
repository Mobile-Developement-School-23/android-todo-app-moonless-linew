package ru.linew.todoapp.presentation.application

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import ru.linew.todoapp.di.component.AppComponent
import ru.linew.todoapp.di.component.DaggerAppComponent
import ru.linew.todoapp.di.module.ApplicationModule
import ru.linew.todoapp.presentation.feature.background.BackgroundWorkerClass
import ru.linew.todoapp.presentation.feature.background.BackgroundWorkerFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TodoApp : Application(), Configuration.Provider {
    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerFactory: BackgroundWorkerFactory
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(context = applicationContext))
            .build()
        appComponent.injectApplication(this)
//        val uploadWorkRequest = PeriodicWorkRequestBuilder<BackgroundWorkerClass>(8, TimeUnit.HOURS).build()
//        WorkManager.getInstance(this).enqueue(uploadWorkRequest)
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory).build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TodoApp -> appComponent
        else -> this.applicationContext.appComponent
    }