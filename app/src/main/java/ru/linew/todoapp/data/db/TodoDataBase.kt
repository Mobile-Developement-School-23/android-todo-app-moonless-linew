package ru.linew.todoapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.linew.todoapp.data.db.model.TodoItemEntity

@Database(entities = [TodoItemEntity::class], version = 1)
abstract class TodoDataBase: RoomDatabase() {
    abstract fun provideTodoDao(): TodoDao
}
