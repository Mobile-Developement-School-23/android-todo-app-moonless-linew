package ru.linew.todoapp.presentation.feature.list.ui.recycler

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.TodoItemBinding
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.utils.toDateFormat

class TodoListAdapter(
    private val onTodoViewClick: (View, TodoItem) -> Unit,
    private val onCheckBoxClick: (Boolean, TodoItem) -> Unit
) :
    ListAdapter<TodoItem, TodoListAdapter.ViewHolder>(ItemCallback) {
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
        holder.onBind(
            getItem(position),
            onTodoClick = onTodoViewClick,
            onCheckBoxClick = onCheckBoxClick
        )
    }

    class ViewHolder(private val binding: TodoItemBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun onBind(
            item: TodoItem,
            onTodoClick: (View, TodoItem) -> Unit,
            onCheckBoxClick: (Boolean, TodoItem) -> Unit
        ) {
            bindTransitionName(item.id)
            bindBodyTextView(item.body, item.isCompleted)
            bindPriorityIcon(item.priority)
            bindCheckBox(item.isCompleted)
            bindMakeUntilTextView(item.deadlineTime)
            binding.root.setOnClickListener {
                onTodoClick(binding.root, item)
            }
            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                onCheckBoxClick(isChecked, item)
            }

        }

        private fun bindBodyTextView(text: String, isCompleted: Boolean) {
            binding.todoBody.text = text
            if (isCompleted) {
                binding.todoBody.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.todoBody.paintFlags =
                    binding.todoBody.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        private fun bindCheckBox(isCompleted: Boolean) {
            binding.checkBox.isChecked = isCompleted
            if (isCompleted) binding.checkBox.isErrorShown = false
        }

        private fun bindMakeUntilTextView(deadlineTime: Long?) {
            if (deadlineTime != null) {
                binding.makeUntilTextView.text = deadlineTime.toDateFormat()
                binding.makeUntilTextView.visibility = View.VISIBLE
            }
        }

        private fun bindTransitionName(id: String) {
            binding.root.transitionName = id
        }

        private fun bindPriorityIcon(priority: Priority) {
            when (priority) {
                Priority.LOW -> binding.priorityIcon.apply {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.low_priority)
                }

                Priority.NO -> binding.priorityIcon.visibility = View.GONE
                Priority.HIGH -> {
                    binding.priorityIcon.apply {
                        visibility = View.VISIBLE
                        setImageResource(R.drawable.high_priority)
                    }
                    binding.checkBox.isErrorShown = true

                }
            }
        }
    }
}

