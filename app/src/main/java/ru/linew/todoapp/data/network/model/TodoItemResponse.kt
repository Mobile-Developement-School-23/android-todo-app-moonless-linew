package ru.linew.todoapp.data.network.model
import com.google.gson.annotations.SerializedName
import ru.linew.todoapp.data.model.TodoItemData

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
fun TodoItemResponse.toData(): TodoItemData =
    TodoItemData(
        id = id,
        body = body,
        priority = renamePriorityToData(priority),
        deadlineTime = deadlineTime,
        isCompleted = isCompleted,
        creationTime = creationTime,
        modificationTime = modificationTime
    )

private fun renamePriorityToData(priority: String): String {
    return when(priority){
        "basic" -> "NO"
        "low" -> "LOW"
        "important" -> "HIGH"
        else -> throw IllegalArgumentException()
    }
}
