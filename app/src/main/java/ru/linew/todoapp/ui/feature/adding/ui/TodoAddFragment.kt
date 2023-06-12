package ru.linew.todoapp.ui.feature.adding.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.platform.MaterialContainerTransform
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.FragmentTodoAddBinding

class TodoAddFragment: Fragment(R.layout.fragment_todo_add) {
    private val binding: FragmentTodoAddBinding by viewBinding()
    private val menu by lazy {
        PopupMenu(requireContext(), binding.priority).also {
            it.inflate(R.menu.priority_menu)
            it.setForceShowIcon(true)
            it.setOnMenuItemClickListener {menuItem ->
                binding.currentPriority.text = menuItem.title
                return@setOnMenuItemClickListener true
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
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