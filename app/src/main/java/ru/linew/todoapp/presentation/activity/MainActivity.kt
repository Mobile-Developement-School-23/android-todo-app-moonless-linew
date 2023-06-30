package ru.linew.todoapp.presentation.activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.linew.todoapp.R
import ru.linew.todoapp.presentation.application.appComponent
import ru.linew.todoapp.shared.Constants
import java.util.UUID
import javax.inject.Inject


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var authContract: AuthContract

    private lateinit var register: ActivityResultLauncher<String>


    private val authDialog by lazy {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.auth_dialog_title))
            .setMessage(resources.getString(R.string.auth_dialog_message))
            .setNeutralButton(resources.getString(R.string.auth_dialog_neutral)) { dialog, which ->
                sharedPreferences.edit()
                    .putBoolean(Constants.SHARED_PREFERENCES_AUTH_STATE_KEY, true)
                    .apply()
            }
            .setNegativeButton(resources.getString(R.string.auth_dialog_negative)) { dialog, which ->

            }
            .setPositiveButton(resources.getString(R.string.auth_dialog_positive)) { dialog, which ->
                register.launch("")
            }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appComponent.injectMainActivity(this)
        register =
            registerForActivityResult(authContract) {
                sharedPreferences.edit()
                    .putBoolean(Constants.SHARED_PREFERENCES_AUTH_STATE_KEY, it)
                    .apply()
            }
        if (sharedPreferences.getString(Constants.SHARED_PREFERENCES_UNIQUE_ID_KEY, null) == null) {
            sharedPreferences.edit()
                .putString(Constants.SHARED_PREFERENCES_UNIQUE_ID_KEY, UUID.randomUUID().toString())
                .apply()
        }
        if (!sharedPreferences.getBoolean(Constants.SHARED_PREFERENCES_AUTH_STATE_KEY, false)) {
            authDialog.show()
        }


    }

}