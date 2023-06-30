package ru.linew.todoapp.di.module

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import ru.linew.todoapp.shared.Token
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
object ClientModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .addInterceptor {
                val request =
                    it.request().newBuilder().addHeader("Authorization", Token.Token).build()
                var response = it.proceed(request)
                var tryCount = 0
                while (!response.isSuccessful && tryCount < 2) {
                    Log.d("intercept", "Request is not successful - $tryCount");
                    tryCount++;
                    response.close()
                    response = it.proceed(request);
                }
                return@addInterceptor response
            }
            .build()
    }
}