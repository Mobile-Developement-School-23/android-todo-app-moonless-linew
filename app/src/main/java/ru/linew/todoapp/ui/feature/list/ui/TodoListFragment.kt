package ru.linew.todoapp.ui.feature.list.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.linew.todoapp.R
import ru.linew.todoapp.data.mapper.toUiLayer
import ru.linew.todoapp.data.repository.TodoItemsRepositoryImpl
import ru.linew.todoapp.databinding.FragmentTodoListBinding
import ru.linew.todoapp.ui.feature.list.ui.recycler.TodoListAdapter

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    val binding: FragmentTodoListBinding by viewBinding()
    val adapter = TodoListAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todoList.adapter = adapter
        adapter.submitList(TodoItemsRepositoryImpl().todos.map { it.toUiLayer() })

    }
}