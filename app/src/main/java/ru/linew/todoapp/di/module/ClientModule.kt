package ru.linew.todoapp.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import ru.linew.todoapp.shared.Token
import javax.inject.Singleton

@Module
class ClientModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor {
                it.proceed(
                    it.request().newBuilder().addHeader("Authorization", Token.Token).build()
                )
            }
            .build()
    }
}