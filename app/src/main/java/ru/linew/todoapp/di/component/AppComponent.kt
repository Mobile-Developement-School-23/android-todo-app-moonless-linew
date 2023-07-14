package ru.linew.todoapp.di.component

import dagger.Component
import ru.linew.todoapp.di.module.ApiModule
import ru.linew.todoapp.di.module.ApplicationModule
import ru.linew.todoapp.di.module.ClientModule
import ru.linew.todoapp.di.module.DataSourceModule
import ru.linew.todoapp.di.module.DatabaseModule
import ru.linew.todoapp.di.module.RepositoryModule
import ru.linew.todoapp.di.scope.AppScope
import ru.linew.todoapp.presentation.activity.MainActivity
import ru.linew.todoapp.presentation.background.repository.DeadlineTodoProvider
import ru.linew.todoapp.presentation.background.sync.BackgroundWorkerFactory

@AppScope
@Component(modules = [RepositoryModule::class, ApplicationModule::class, ClientModule::class,
    ApiModule::class, DataSourceModule::class, DatabaseModule::class])
interface AppComponent {
    fun injectMainActivity(activity: MainActivity)
    fun provideWorkerFactory(): BackgroundWorkerFactory
    fun provideDeadlineTodoProvider(): DeadlineTodoProvider
    fun addFragmentComponent(): EditorFragmentComponent
    fun listFragmentComponent(): ListFragmentComponent
}
