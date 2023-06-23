package ru.linew.todoapp.data.retrofit.model
import com.google.gson.annotations.SerializedName
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem

data class TodoItemResponse(
    @SerializedName("changed_at")
    val modificationTime: Long,
    @SerializedName("color")
    val color: String?,
    @SerializedName("created_at")
    val creationTime: Long,
    @SerializedName("deadline")
    val deadlineTime: Long?,
    @SerializedName("done")
    val isCompleted: Boolean,
    @SerializedName("id")
    val id: String,
    @SerializedName("importance")
    val priority: String,
    @SerializedName("last_updated_by")
    val lastUpdatedBy: String,
    @SerializedName("text")
    val body: String
)

fun TodoItemResponse.toUiLayer(): TodoItem =
    TodoItem(
        id = id,
        body = body,
        priority = Priority.valueOf(priority),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime
    )