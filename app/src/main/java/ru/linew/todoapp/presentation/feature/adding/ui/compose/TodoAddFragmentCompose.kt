package ru.linew.todoapp.presentation.feature.adding.ui.compose

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.transition.platform.MaterialContainerTransform
import ru.linew.todoapp.databinding.FragmentTodoAddComposeBinding

class TodoAddFragmentCompose: Fragment() {
    private var _binding: FragmentTodoAddComposeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoAddComposeBinding.inflate(inflater, container, false)
        binding.composeView.apply {
            setContent {
                AddTodoUiWithTheme()
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

}
