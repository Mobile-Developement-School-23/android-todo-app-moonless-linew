package ru.linew.todoapp.di.module

import dagger.Binds
import dagger.Module
import ru.linew.todoapp.data.datasource.remote.TodoRemoteDataSourceImpl
import ru.linew.todoapp.data.repository.datasource.TodoRemoteDataSource


@Module
interface DataSourceModule {

    @Binds
    fun bindRemoteDataSource(todoRemoteDataSourceImpl: TodoRemoteDataSourceImpl): TodoRemoteDataSource

}