package ru.linew.todoapp.ui.feature.list.ui.recycler

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.checkbox.MaterialCheckBox
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.TodoItemBinding
import ru.linew.todoapp.ui.feature.list.model.Priority
import ru.linew.todoapp.ui.feature.list.model.TodoItem

class TodoListAdapter(val onTodoClick: (View, TodoItem) -> Unit) : ListAdapter<TodoItem, TodoListAdapter.ViewHolder>(ItemCallback) {
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
        holder.onBind(getItem(position), onTodoClick = onTodoClick)
    }

    class ViewHolder(private val binding: TodoItemBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: TodoItem, onTodoClick: (View, TodoItem) -> Unit) {
            binding.todoBody.text = item.body
            binding.root.transitionName = item.id
            when (item.priority) {
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
                    binding.checkBox.addOnCheckedStateChangedListener { checkBox, state ->
                        checkBox.isErrorShown = MaterialCheckBox.STATE_CHECKED != state
                    }

                }
            }
            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    with(binding.todoBody) {
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                } else {
                    with(binding.todoBody) {
                        paintFlags =
                            binding.todoBody.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    }
                }
            }
            binding.root.setOnClickListener {
                onTodoClick(binding.root, item)
            }


        }
    }
}

