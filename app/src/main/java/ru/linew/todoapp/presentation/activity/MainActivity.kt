package ru.linew.todoapp.presentation.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.linew.todoapp.R
import ru.linew.todoapp.presentation.application.appComponent
import ru.linew.todoapp.presentation.background.notifications.NotificationsWorker
import ru.linew.todoapp.presentation.model.ThemeMode
import ru.linew.todoapp.shared.Constants
import java.time.Duration
import java.util.UUID
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    private lateinit var themeMode: ThemeMode


    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.injectMainActivity(this)
        themeMode = ThemeMode.valueOf(
            sharedPreferences.getString(
                Constants.SHARED_PREFERENCES_THEME_KEY,
                Constants.SHARED_PREFERENCES_THEME_DEFAULT
            )!!
        )
        setThemeMode()
        setContentView(R.layout.activity_main)
        checkNotificationsPermission()
        setupUniqueId()

    }


    fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.choose_theme))
            .setPositiveButton(R.string.ok) { p1, p2 ->
                sharedPreferences.edit().putString(
                    Constants.SHARED_PREFERENCES_THEME_KEY,
                    themeMode.toString()
                ).apply()
                setThemeMode()
            }
            .setNegativeButton(R.string.cancel) { p1, p2 ->
                themeMode =
                    ThemeMode.valueOf(
                        sharedPreferences.getString(
                            Constants.SHARED_PREFERENCES_THEME_KEY,
                            Constants.SHARED_PREFERENCES_THEME_DEFAULT
                        )!!
                    )
            }
            .setSingleChoiceItems(
                R.array.theme_items,
                themeMode.toInt()
            ) { _, themeItemIndex ->
                when (themeItemIndex) {
                    0 -> themeMode = ThemeMode.LIGHT
                    1 -> themeMode = ThemeMode.DARK
                    2 -> themeMode = ThemeMode.SYSTEM
                }
            }.show()
    }

    private fun showNotificationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.notification_request))
            .setPositiveButton(R.string.yes) { _, _ ->
                showNotificationsEnabled()
                checkNotificationsPermission()
            }
            .setNegativeButton(R.string.no) { _, _ ->
                showNotificationsDisabled()
    }.show()}

    private fun setThemeMode() = when (themeMode) {
        ThemeMode.LIGHT -> setLightMode()
        ThemeMode.DARK -> setDarkMode()
        ThemeMode.SYSTEM -> setSystemMode()
    }

    private fun showNotificationsEnabled() = sharedPreferences.edit().putString(
        Constants.SHARED_PREFERENCES_NOTIFICATIONS_KEY,
        "YES"
    ).apply()

    private fun showNotificationsDisabled() = sharedPreferences.edit().putString(
        Constants.SHARED_PREFERENCES_NOTIFICATIONS_KEY,
        "NO"
    ).apply()

    private fun checkNotificationsPermission() {
        when(sharedPreferences.getString(Constants.SHARED_PREFERENCES_NOTIFICATIONS_KEY, "null")){
            "null" -> showNotificationDialog()
            "YES" -> setupNotificationsListener()
            "NO" -> {}
        }
    }


    private fun setDarkMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    private fun setLightMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    private fun setSystemMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

    private fun setupUniqueId() {
        if (sharedPreferences.getString(Constants.SHARED_PREFERENCES_UNIQUE_ID_KEY, null) == null) {
            sharedPreferences.edit()
                .putString(Constants.SHARED_PREFERENCES_UNIQUE_ID_KEY, UUID.randomUUID().toString())
                .apply()
        }
    }

    private fun setupNotificationsListener(){
        val delay = Constants.MILLIS_IN_DAY - (System.currentTimeMillis() % Constants.MILLIS_IN_DAY) - Constants.UTC_3_OFFSET
        val request = PeriodicWorkRequest.Builder(NotificationsWorker::class.java, 1, TimeUnit.DAYS)
            .setInitialDelay(Duration.ofMillis(delay))
            .addTag(Constants.NOTIFICATION_WORKER_TAG)
            .build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(Constants.NOTIFICATION_WORKER_TAG, ExistingPeriodicWorkPolicy.KEEP, request)
    }
}