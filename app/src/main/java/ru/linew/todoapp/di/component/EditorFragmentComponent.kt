package ru.linew.todoapp.di.component

import dagger.Subcomponent
import ru.linew.todoapp.di.scope.AddFragmentScope
import ru.linew.todoapp.presentation.feature.adding.view.viewmodel.TodoEditorViewModel


@Subcomponent
@AddFragmentScope
interface EditorFragmentComponent {

    @AddFragmentScope
    fun provideTodoEditorViewModel(): TodoEditorViewModel.TodoEditorViewModelFactory

}
