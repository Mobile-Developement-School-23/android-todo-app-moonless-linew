package ru.linew.todoapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.linew.todoapp.data.db.model.TodoItemEntity

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos")
    suspend fun provideListOfTodos(): List<TodoItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo: TodoItemEntity)
}