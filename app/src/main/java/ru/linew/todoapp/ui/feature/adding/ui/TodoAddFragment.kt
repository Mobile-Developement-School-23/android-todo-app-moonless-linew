package ru.linew.todoapp.ui.feature.adding.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.FragmentTodoAddBinding

class TodoAddFragment: Fragment(R.layout.fragment_todo_add) {
    val binding: FragmentTodoAddBinding by viewBinding()
    val menu by lazy {
        PopupMenu(requireContext(), binding.priority).also {
            it.inflate(R.menu.priority_menu)
            it.setForceShowIcon(true)
            it.setOnMenuItemClickListener {menuItem ->
                binding.currentPriority.text = menuItem.title
                return@setOnMenuItemClickListener true
            }
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            binding.saveButton.setOnClickListener {
                findNavController().navigateUp()
            }
            binding.deleteButton.setOnClickListener {
                findNavController().navigateUp()
            }
            binding.priority.setOnClickListener {
                menu.show()
            }

        }


    }
}