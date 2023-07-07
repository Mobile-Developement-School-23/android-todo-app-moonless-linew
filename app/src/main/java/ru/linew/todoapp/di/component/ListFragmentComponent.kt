package ru.linew.todoapp.di.component

import dagger.Subcomponent
import ru.linew.todoapp.di.scope.ListFragmentScope
import ru.linew.todoapp.presentation.feature.list.viewmodel.TodoListFragmentViewModel

@Subcomponent
@ListFragmentScope
interface ListFragmentComponent {

    @ListFragmentScope
    fun provideTodoListFragmentViewModel(): TodoListFragmentViewModel.TodoListFragmentViewModelFactory
}