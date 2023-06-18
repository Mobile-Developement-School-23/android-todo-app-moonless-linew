package ru.linew.todoapp.di.module

import dagger.Binds
import dagger.Module
import ru.linew.todoapp.data.repository.TodoItemsRepositoryImpl
import ru.linew.todoapp.ui.feature.list.repository.TodoItemsRepository
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindTodoRepository(todoRepository: TodoItemsRepositoryImpl): TodoItemsRepository

}