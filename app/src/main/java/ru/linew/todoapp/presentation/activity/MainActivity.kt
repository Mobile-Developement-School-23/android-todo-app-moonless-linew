package ru.linew.todoapp.presentation.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.linew.todoapp.R
import ru.linew.todoapp.presentation.application.appComponent
import ru.linew.todoapp.presentation.feature.background.BackgroundWorkerClass
import ru.linew.todoapp.shared.Constants
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.injectMainActivity(this)
        if (sharedPreferences.getString(Constants.SHARED_PREFERENCES_UNIQUE_ID_KEY, null) == null) {
            sharedPreferences.edit()
                .putString(Constants.SHARED_PREFERENCES_UNIQUE_ID_KEY, UUID.randomUUID().toString())
                .apply()
        }


    }
}