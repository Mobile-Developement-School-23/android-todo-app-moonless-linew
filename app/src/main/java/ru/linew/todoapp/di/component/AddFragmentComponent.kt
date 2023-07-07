package ru.linew.todoapp.di.component

import dagger.Subcomponent
import ru.linew.todoapp.di.scope.AddFragmentScope
import ru.linew.todoapp.presentation.feature.adding.viewmodel.TodoAddFragmentViewModel


@Subcomponent
@AddFragmentScope
interface AddFragmentComponent {

    @AddFragmentScope
    fun provideTodoAddFragmentViewModel(): TodoAddFragmentViewModel.TodoAddFragmentViewModelFactory
}
