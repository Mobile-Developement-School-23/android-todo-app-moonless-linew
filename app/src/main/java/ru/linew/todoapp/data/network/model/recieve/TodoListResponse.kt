package ru.linew.todoapp.data.network.model.recieve
import com.google.gson.annotations.SerializedName
import ru.linew.todoapp.data.network.model.TodoItemResponse

data class TodoListResponse(
    @SerializedName("list")
    val list: List<TodoItemResponse>,
    @SerializedName("revision")
    val revision: String,
    @SerializedName("status")
    val status: String
)
