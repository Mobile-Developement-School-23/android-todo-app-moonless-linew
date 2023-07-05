package ru.linew.todoapp.presentation.feature.list.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import ru.linew.todoapp.presentation.model.TodoItem

object  ItemCallback: DiffUtil.ItemCallback<TodoItem>() {
    override fun areItemsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem.id == newItem.id
    }
    override fun areContentsTheSame(oldItem: TodoItem, newItem: TodoItem): Boolean {
        return oldItem == newItem
    }
}
