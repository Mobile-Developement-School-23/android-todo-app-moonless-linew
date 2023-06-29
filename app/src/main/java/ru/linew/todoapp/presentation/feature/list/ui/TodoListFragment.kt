package ru.linew.todoapp.presentation.feature.list.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.FragmentTodoListBinding
import ru.linew.todoapp.presentation.application.appComponent
import ru.linew.todoapp.presentation.feature.list.ui.recycler.TodoListAdapter
import ru.linew.todoapp.presentation.feature.list.viewmodel.TodoListFragmentViewModel
import ru.linew.todoapp.presentation.feature.list.viewmodel.state.ErrorState
import ru.linew.todoapp.presentation.feature.list.viewmodel.state.Result
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.shared.Constants

class TodoListFragment : Fragment(R.layout.fragment_todo_list) {
    private val binding: FragmentTodoListBinding by viewBinding()
    private val viewModel: TodoListFragmentViewModel by viewModels {
        TodoListFragmentViewModel.Factory(
            requireActivity().appComponent.injectTodoListFragmentViewModel()
        )
    }
    private var visibilityState = true
    private val itemClickCallback: (View, TodoItem) -> Unit = { view: View, todoItem: TodoItem ->
        val extras = FragmentNavigatorExtras(view to getString(R.string.card_edit_transition))
        val bundle = Bundle().apply {
            putString(Constants.TODO_ID_ARGUMENT_KEY, todoItem.id)
        }
        findNavController().navigate(
            R.id.action_todoListFragment_to_todoAddFragment, bundle, null, extras
        )
    }
    private val visibilityChangedCallback: (isVisible: Boolean) -> Unit = {
        if (viewModel.todos.value != Result.Null) {
            if (it) {
                adapter.submitList((viewModel.todos.value as Result.Success).result)
            } else {
                adapter.submitList((viewModel.todos.value as Result.Success)
                    .result
                    .filter { todoItem -> !todoItem.isCompleted })
            }
        }
    }

    //в onPause
    private val checkBoxChangedCallback: (Boolean, TodoItem) -> Unit = { isCompleted, todoItem ->
        todoItem.isCompleted = isCompleted
        viewModel.todoCompleteStatusChanged(todoItem)
    }
    private val adapter = TodoListAdapter(itemClickCallback, checkBoxChangedCallback)

    override fun onStart() {
        super.onStart()
        viewModel.todos.observe(viewLifecycleOwner) {
            when (it) {
                Result.Null -> {}
                is Result.Success -> {
                    binding.todoList.hideShimmer()
                    adapter.submitList(it.result)
                }
            }

        }
        viewModel.errorState.observe(viewLifecycleOwner) {
            when (it) {
                ErrorState.Error -> Snackbar.make(
                    binding.root, R.string.error, Snackbar.LENGTH_SHORT
                ).show()

                ErrorState.Ok -> {}
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        viewModel.setupViewModelListener()
        binding.todoList.adapter = adapter
        binding.todoList.showShimmer()
        setupVisibilityButton()
        setupNewTodoFab()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        exitTransition = null
        reenterTransition = null

    }

    private fun setupNewTodoFab() {
        binding.addTodo.setOnClickListener {
            findNavController().navigate(R.id.action_todoListFragment_to_todoAddFragment)
        }
    }

    private fun setupVisibilityButton() {
        binding.visibilityIcon.apply {
            setOnClickListener {
                visibilityState = if (!visibilityState) {
                    setImageResource(R.drawable.visibility)
                    visibilityChangedCallback(true)
                    true
                } else {
                    setImageResource(R.drawable.visibility_off)
                    visibilityChangedCallback(false)
                    false
                }
            }
        }
    }
}