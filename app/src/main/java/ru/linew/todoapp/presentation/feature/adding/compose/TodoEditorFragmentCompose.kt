package ru.linew.todoapp.presentation.feature.adding.compose

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.platform.MaterialContainerTransform
import kotlinx.coroutines.launch
import ru.linew.todoapp.databinding.FragmentTodoAddComposeBinding
import ru.linew.todoapp.presentation.application.appComponent
import ru.linew.todoapp.presentation.feature.adding.compose.ui.TodoEditorUiWithTheme
import ru.linew.todoapp.presentation.feature.adding.view.viewmodel.TodoEditorViewModel
import ru.linew.todoapp.presentation.feature.adding.view.viewmodel.state.EditStatus
import ru.linew.todoapp.presentation.model.EditFragmentMode
import ru.linew.todoapp.shared.Constants

class TodoEditorFragmentCompose : Fragment() {
    private var _binding: FragmentTodoAddComposeBinding? = null
    private val binding get() = _binding!!
    private val component by lazy {
        requireActivity().appComponent.addFragmentComponent()
    }
    private val viewModel: TodoEditorViewModel by viewModels {
        TodoEditorViewModel.Factory(
            component.provideTodoEditorViewModel()
        )
    }
    private val itemId by lazy {
        arguments?.getString(Constants.TODO_ID_ARGUMENT_KEY)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.onCreate(itemId)
        setupExitListener()
        _binding = FragmentTodoAddComposeBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setContent {
                TodoEditorUiWithTheme(
                    uiState = viewModel.currentTodo.collectAsState(),
                    if(arguments == null) EditFragmentMode.ADDING else EditFragmentMode.UPDATING,
                    onBodyTextChanged = { viewModel.todoBodyTextChanged(it) },
                    onCloseButtonClick = { findNavController().navigateUp() },
                    onDateChange = { viewModel.todoDeadlineTimeChanged(it) },
                    onSaveButtonClick = { viewModel.saveButtonClicked() },
                    onDeleteButtonClick = { viewModel.deleteButtonClicked(itemId!!) },
                    onPriorityItemClick = { viewModel.todoPriorityChanged(it) }
                )
            }
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            scrimColor = Color.TRANSPARENT
        }
    }

    private fun setupExitListener() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.currentEditStatus.collect {
                when (it) {
                    EditStatus.InProcess -> {}
                    EditStatus.Null -> {}
                    EditStatus.Done -> findNavController().navigateUp()
                }

            }
        }
    }

}

