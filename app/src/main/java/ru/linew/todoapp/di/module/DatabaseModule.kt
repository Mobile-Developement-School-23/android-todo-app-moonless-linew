package ru.linew.todoapp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.linew.todoapp.data.db.TodoDao
import ru.linew.todoapp.data.db.TodoDataBase
import javax.inject.Singleton

@Module
object DataBaseModule {

    @Singleton
    @Provides
    fun provideTracksDao(database: TodoDataBase): TodoDao{
        return database.provideTodoDao()
    }

    @Singleton
    @Provides
    fun provideDataBase(context: Context): TodoDataBase{
        return Room.databaseBuilder(context,
            TodoDataBase::class.java,
            "todo").build()
    }
}