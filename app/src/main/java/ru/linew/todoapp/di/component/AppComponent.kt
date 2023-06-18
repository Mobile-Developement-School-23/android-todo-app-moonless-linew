package ru.linew.todoapp.di.component

import dagger.Component
import ru.linew.todoapp.di.module.ApplicationModule
import ru.linew.todoapp.di.module.DataSourceModule
import ru.linew.todoapp.di.module.RepositoryModule
import ru.linew.todoapp.ui.feature.adding.viewmodel.TodoAddFragmentViewModel
import ru.linew.todoapp.ui.feature.list.viewmodel.TodoListFragmentViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, DataSourceModule::class, ApplicationModule::class])
interface AppComponent {


    fun injectTodoListFragmentViewModel(): TodoListFragmentViewModel.TodoListFragmentViewModelFactory

    fun injectTodoAddFragmentViewModel(): TodoAddFragmentViewModel.TodoAddFragmentViewModelFactory


}