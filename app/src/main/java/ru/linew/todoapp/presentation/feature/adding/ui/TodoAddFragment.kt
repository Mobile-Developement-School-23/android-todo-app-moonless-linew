package ru.linew.todoapp.presentation.feature.adding.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.platform.MaterialContainerTransform
import kotlinx.coroutines.launch
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.FragmentTodoAddBinding
import ru.linew.todoapp.presentation.application.appComponent
import ru.linew.todoapp.presentation.feature.adding.viewmodel.TodoAddFragmentViewModel
import ru.linew.todoapp.presentation.feature.adding.viewmodel.state.Result
import ru.linew.todoapp.presentation.model.Priority
import ru.linew.todoapp.presentation.model.TodoItem
import ru.linew.todoapp.presentation.utils.toDateFormat
import ru.linew.todoapp.shared.Constants

class TodoAddFragment : Fragment(R.layout.fragment_todo_add) {
    private val binding: FragmentTodoAddBinding by viewBinding()
    private val viewModel: TodoAddFragmentViewModel by viewModels {
        TodoAddFragmentViewModel.Factory(
            requireActivity().appComponent.injectTodoAddFragmentViewModel()
        )
    }
    private val menu by lazy {
        PopupMenu(requireContext(), binding.priority).also {
            it.inflate(R.menu.priority_menu)
            it.setForceShowIcon(true)
            it.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.high_item -> {
                        viewModel.todoPriorityChanged(Priority.HIGH)
                    }

                    R.id.no_item -> {
                        viewModel.todoPriorityChanged(Priority.NO)
                    }

                    R.id.low_item -> {
                        viewModel.todoPriorityChanged(Priority.LOW)
                    }

                }
                binding.currentPriority.text = menuItem.title
                return@setOnMenuItemClickListener true
            }
        }
    }
    private val datePicker by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.choose_date))
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }
    private val itemId by lazy {
        arguments?.getString(Constants.TODO_ID_ARGUMENT_KEY)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onCreate(itemId)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
            viewModel.currentTodo.collect{
                    if (it is Result.Complete){
                        loadFromViewModel(it.result)
                    }
                }

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }


    private fun setupUi() {
        //loadFromViewModel()
        setupInputBodyTextView()
        setupMakeUntilSwitch()
        setupPriorityButton()
        setupDatePicker()
        setupNavButtons()
    }

    private fun loadFromViewModel(todoItem: TodoItem) {
        binding.inputBody.setText(todoItem.body)
        val priorityText = when (todoItem.priority) {
            Priority.LOW -> getString(R.string.low)
            Priority.NO -> getString(R.string.no)
            Priority.HIGH -> getString(R.string.high)
        }
        binding.currentPriority.text = priorityText
        if (todoItem.deadlineTime != null) {
            setMakeUntilDate(todoItem.deadlineTime!!)
            binding.makeUntil.visibility = View.VISIBLE
            binding.makeUntilSwitch.isChecked = true
        } else {
            binding.makeUntilSwitch.isChecked = false
        }
    }

    private fun setupNavButtons() {
        with(binding) {
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
            saveButton.setOnClickListener {
                viewModel.addOrUpdateTodo()
                findNavController().navigateUp()
            }
            deleteButton.setOnClickListener {
                viewModel.deleteItemClicked(arguments?.getString(Constants.TODO_ID_ARGUMENT_KEY, "")!!)
                findNavController().navigateUp()
            }
            if (arguments == null) {
                binding.deleteButton.visibility = View.INVISIBLE
            }
        }
    }

    private fun setupInputBodyTextView() {
        with(binding.inputBody) {
            doOnTextChanged { text, _, _, _ ->
                viewModel.todoBodyTextChanged(text.toString())
            }
        }
    }

    private fun setupMakeUntilSwitch() {
        with(binding.makeUntilSwitch) {
            isSaveEnabled = false
            setOnClickListener {
                if (isChecked) {
                    datePicker.show(parentFragmentManager, null)
                } else {
                    binding.makeUntil.visibility = View.INVISIBLE
                    viewModel.todoDeadlineTimeChanged(null)
                }
            }
        }
    }

    private fun setupPriorityButton() {
        with(binding.priority) {
            setOnClickListener {
                menu.show()
            }
        }
    }

    private fun setupDatePicker() {
        with(binding) {
            datePicker.addOnNegativeButtonClickListener {
                makeUntilSwitch.isChecked = false
                makeUntil.visibility = View.INVISIBLE
            }
            datePicker.addOnCancelListener {
                makeUntilSwitch.isChecked = false
                makeUntil.visibility = View.INVISIBLE
            }
            datePicker.addOnPositiveButtonClickListener {
                viewModel.todoDeadlineTimeChanged(it)
                makeUntilSwitch.isChecked = true
                setMakeUntilDate(it)

                makeUntil.visibility = View.VISIBLE

            }
        }
    }

    private fun setMakeUntilDate(millis: Long) {
        binding.makeUntil.text = millis.toDateFormat()
    }
}