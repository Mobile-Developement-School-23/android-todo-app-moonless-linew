package ru.linew.todoapp.presentation.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.linew.todoapp.R
import ru.linew.todoapp.presentation.application.appComponent
import ru.linew.todoapp.presentation.model.ThemeMode
import ru.linew.todoapp.presentation.model.toInt
import ru.linew.todoapp.shared.Constants
import java.util.UUID
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
                "SYSTEM"
            )!!
        )
        setThemeMode()
        setContentView(R.layout.activity_main)


        if (sharedPreferences.getString(Constants.SHARED_PREFERENCES_UNIQUE_ID_KEY, null) == null) {
            sharedPreferences.edit()
                .putString(Constants.SHARED_PREFERENCES_UNIQUE_ID_KEY, UUID.randomUUID().toString())
                .apply()
        }

    }


    fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.choose_theme))
            .setPositiveButton(R.string.ok) { p1, p2 ->
                sharedPreferences.edit().putString (
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
                            "SYSTEM"
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

    private fun setThemeMode() = when(themeMode){
        ThemeMode.LIGHT -> setLightMode()
        ThemeMode.DARK -> setDarkMode()
        ThemeMode.SYSTEM -> setSystemMode()
    }

    private fun setDarkMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    private fun setLightMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

    private fun setSystemMode() =
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
}