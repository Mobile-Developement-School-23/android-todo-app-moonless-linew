package ru.linew.todoapp.ui.feature.adding.ui

import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.FragmentTodoAddBinding

class TodoAddFragment: Fragment(R.layout.fragment_todo_add) {
    val binding: FragmentTodoAddBinding by viewBinding()
}