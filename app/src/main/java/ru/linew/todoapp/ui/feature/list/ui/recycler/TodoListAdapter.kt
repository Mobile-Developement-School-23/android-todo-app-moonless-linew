package ru.linew.todoapp.ui.feature.list.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.TodoItemBinding
import ru.linew.todoapp.ui.feature.list.model.Importance
import ru.linew.todoapp.ui.feature.list.model.TodoItem

class TodoListAdapter : ListAdapter<TodoItem, TodoListAdapter.ViewHolder>(ItemCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }
    class ViewHolder(private val binding: TodoItemBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: TodoItem) {
            binding.todoBody.text = item.body
            when (item.importance) {
                Importance.LOW -> binding.priorityIcon.apply {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.low_priority)
                }
                Importance.MEDIUM -> binding.priorityIcon.visibility = View.GONE
                Importance.HIGH -> binding.priorityIcon.apply {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.high_priority)
                }
            }

        }
    }
}

