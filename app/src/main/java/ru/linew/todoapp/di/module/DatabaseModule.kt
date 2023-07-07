package ru.linew.todoapp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.linew.todoapp.data.db.TodoDao
import ru.linew.todoapp.data.db.TodoDataBase
import ru.linew.todoapp.di.scope.AppScope

@Module
object DatabaseModule {

    @AppScope
    @Provides
    fun provideTracksDao(database: TodoDataBase): TodoDao{
        return database.provideTodoDao()
    }

    @AppScope
    @Provides
    fun provideDataBase(context: Context): TodoDataBase{
        return Room.databaseBuilder(context,
            TodoDataBase::class.java,
            "todo").build()
    }
}
