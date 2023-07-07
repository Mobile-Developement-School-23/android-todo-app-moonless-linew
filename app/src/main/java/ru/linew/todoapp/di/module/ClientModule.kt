package ru.linew.todoapp.di.module

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import ru.linew.todoapp.di.scope.AppScope
import ru.linew.todoapp.shared.Token

@Module
object ClientModule {
    @AppScope
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor {
                val request =
                    it.request().newBuilder().addHeader("Authorization", Token.BEARER_TOKEN).build()
                var response = it.proceed(request)
                var tryCount = 0
                while (!response.isSuccessful && tryCount < 2) {
                    Log.d("intercept", "Request is not successful - $tryCount")
                    tryCount++
                    response.close()
                    response = it.proceed(request)
                }
                return@addInterceptor response
            }
            .build()
    }
}
