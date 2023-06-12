package ru.linew.todoapp.ui.feature.list.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.FragmentTodoListBinding

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    val binding: FragmentTodoListBinding by viewBinding()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_todoListFragment_to_todoAddFragment)
        }
    }
}