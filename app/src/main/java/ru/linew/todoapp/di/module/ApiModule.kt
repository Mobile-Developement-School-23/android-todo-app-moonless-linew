package ru.linew.todoapp.di.module

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.linew.todoapp.data.network.TodoApiService
import ru.linew.todoapp.shared.Constants
import javax.inject.Singleton

@Module
object ApiModule {
    @Singleton
    @Provides
    fun provideRetrofitClient(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    @Provides
    fun provideTodoApiService(retrofitClient: Retrofit): TodoApiService{
        return retrofitClient.create(TodoApiService::class.java)
    }
}