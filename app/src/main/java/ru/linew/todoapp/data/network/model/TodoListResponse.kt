package ru.linew.todoapp.data.network.model
import com.google.gson.annotations.SerializedName

data class TodoListResponse(
    @SerializedName("list")
    val list: List<TodoItemResponse>,
    @SerializedName("revision")
    val revision: String,
    @SerializedName("status")
    val status: String
)