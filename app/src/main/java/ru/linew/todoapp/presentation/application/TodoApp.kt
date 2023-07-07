package ru.linew.todoapp.presentation.application

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.linew.todoapp.di.component.AppComponent
import ru.linew.todoapp.di.component.DaggerAppComponent
import ru.linew.todoapp.di.module.ApplicationModule
import ru.linew.todoapp.presentation.background.BackgroundWorkerClass
import ru.linew.todoapp.presentation.background.BackgroundWorkerFactory
import ru.linew.todoapp.shared.Constants
import java.util.concurrent.TimeUnit

class TodoApp : Application(), Configuration.Provider {
    lateinit var appComponent: AppComponent
    lateinit var workerFactory: BackgroundWorkerFactory
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .applicationModule(ApplicationModule(context = applicationContext))
            .build()
        workerFactory = appComponent.provideWorkerFactory()
        enqueueBackgroundUpdater(configureBackgroundUpdater())
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory).build()
    }

    private fun configureBackgroundUpdater() =
        PeriodicWorkRequestBuilder<BackgroundWorkerClass>(
            Constants.REPEAT_INTERVAL_IN_HOURS,
            TimeUnit.HOURS
        ).setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).build()

    private fun enqueueBackgroundUpdater(request: PeriodicWorkRequest) {
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            Constants.BACKGROUND_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TodoApp -> appComponent
        else -> this.applicationContext.appComponent
    }
