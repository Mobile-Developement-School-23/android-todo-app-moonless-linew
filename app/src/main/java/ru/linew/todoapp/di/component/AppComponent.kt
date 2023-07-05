package ru.linew.todoapp.di.component

import dagger.Component
import ru.linew.todoapp.di.module.ApiModule
import ru.linew.todoapp.di.module.ApplicationModule
import ru.linew.todoapp.di.module.AuthModule
import ru.linew.todoapp.di.module.ClientModule
import ru.linew.todoapp.di.module.DataSourceModule
import ru.linew.todoapp.di.module.DatabaseModule
import ru.linew.todoapp.di.module.RepositoryModule
import ru.linew.todoapp.presentation.activity.MainActivity
import ru.linew.todoapp.presentation.application.TodoApp
import ru.linew.todoapp.presentation.feature.adding.viewmodel.TodoAddFragmentViewModel
import ru.linew.todoapp.presentation.feature.list.viewmodel.TodoListFragmentViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [RepositoryModule::class, ApplicationModule::class, ClientModule::class,
    ApiModule::class, DataSourceModule::class, DatabaseModule::class, AuthModule::class])
interface AppComponent {

    fun injectApplication(application: TodoApp)
    fun injectMainActivity(activity: MainActivity)
    fun injectTodoListFragmentViewModel(): TodoListFragmentViewModel.TodoListFragmentViewModelFactory

    fun injectTodoAddFragmentViewModel(): TodoAddFragmentViewModel.TodoAddFragmentViewModelFactory


}