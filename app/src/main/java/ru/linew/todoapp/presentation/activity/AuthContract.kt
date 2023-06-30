package ru.linew.todoapp.presentation.activity

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.yandex.authsdk.YandexAuthLoginOptions
import com.yandex.authsdk.YandexAuthSdk
import javax.inject.Inject

class AuthContract @Inject constructor(private val authSdk: YandexAuthSdk): ActivityResultContract<String, Boolean>() {
    override fun createIntent(context: Context, input: String): Intent {
        return authSdk.createLoginIntent(YandexAuthLoginOptions.Builder().build())
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {
        return (resultCode == -1)
    }
}