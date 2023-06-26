package ru.linew.todoapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import ru.linew.todoapp.data.db.model.TodoItemEntity

@Dao

interface TodoDao {
    @Query("SELECT * FROM todos")
    suspend fun getListOfTodos(): List<TodoItemEntity>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun getTodoById(id: String): TodoItemEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todoItem: TodoItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListOfTodos(todoItemList: List<TodoItemEntity>)

    @Delete
    suspend fun deleteTodo(todoItem: TodoItemEntity)

    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteTodoById(id: String)

    @Update
    suspend fun updateTodo(todoItem: TodoItemEntity)
}