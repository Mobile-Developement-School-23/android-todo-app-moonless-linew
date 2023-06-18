package ru.linew.todoapp.ui.feature.adding.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.platform.MaterialContainerTransform
import ru.linew.todoapp.R
import ru.linew.todoapp.databinding.FragmentTodoAddBinding
import ru.linew.todoapp.ui.application.appComponent
import ru.linew.todoapp.ui.feature.adding.viewmodel.TodoAddFragmentViewModel
import ru.linew.todoapp.ui.feature.list.ui.utils.Keys
import ru.linew.todoapp.ui.model.Priority
import ru.linew.todoapp.ui.utils.toDateFormat

class TodoAddFragment : Fragment(R.layout.fragment_todo_add) {
    private val binding: FragmentTodoAddBinding by viewBinding()

    private val viewModel: TodoAddFragmentViewModel by viewModels{
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
                        viewModel.priority = Priority.HIGH
                    }
                    R.id.no_item -> {
                        viewModel.priority = Priority.NO
                    }
                    R.id.low_item -> {
                        viewModel.priority = Priority.LOW
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
        }
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()

    }


    private fun setupUi(){
        loadItemById()
        loadFromViewModel()
        setupInputBodyTextView()
        setupMakeUntilSwitch()
        setupPriorityButton()
        setupDatePicker()
        setupNavButtons()
    }
    private fun loadItemById(){
        if (arguments != null){
            val id = arguments?.getString(Keys.TODO_ID_ARGUMENT_KEY)!!
            viewModel.id = id
            viewModel.loadItem(id)
        }
    }
    private fun loadFromViewModel(){
        binding.inputBody.setText(viewModel.body)
        val priorityText = when(viewModel.priority){
            Priority.LOW -> getString(R.string.low)
            Priority.NO -> getString(R.string.no)
            Priority.HIGH -> getString(R.string.high)
        }
        binding.currentPriority.text = priorityText
        if (viewModel.deadlineTime != null){
            setMakeUntilDate(viewModel.deadlineTime!!)
            binding.makeUntil.visibility = View.VISIBLE
            binding.makeUntilSwitch.isChecked = true
        }
        else {
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
                viewModel.deleteItem(arguments?.getString(Keys.TODO_ID_ARGUMENT_KEY, "")!!)
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
                viewModel.body = text.toString()
            }
        }
    }

    private fun setupMakeUntilSwitch() {
        with(binding.makeUntilSwitch) {
            isSaveEnabled = false
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    datePicker.show(parentFragmentManager, null)
                } else {
                    binding.makeUntil.visibility = View.INVISIBLE
                    viewModel.deadlineTime = null
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
                viewModel.deadlineTime = it
                makeUntilSwitch.isChecked = true
                setMakeUntilDate(it)

                makeUntil.visibility = View.VISIBLE

            }
        }
    }

    private fun setMakeUntilDate(millis: Long){
        binding.makeUntil.text = millis.toDateFormat()
    }
}