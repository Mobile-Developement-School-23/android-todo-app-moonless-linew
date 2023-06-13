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
import ru.linew.todoapp.ui.feature.list.interactor.TodoItemsRepository
import ru.linew.todoapp.ui.feature.list.ui.recycler.TodoListAdapter

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    private val binding: FragmentTodoListBinding by viewBinding()
    private val adapter = TodoListAdapter()
    private val repository: TodoItemsRepository = TodoItemsRepositoryImpl()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.todoList.adapter = adapter
        adapter.submitList(repository.provideListOfTodo().map { it.toUiLayer() })
        var visibilityState: Boolean = false
        binding.visibilityIcon.apply {
            setOnClickListener {
                if(visibilityState == false){
                    setImageResource(R.drawable.visibility)
                    visibilityState = true
                }
                else{
                    setImageResource(R.drawable.visibility_off)
                    visibilityState = false
                }
            }
        }
    }
}