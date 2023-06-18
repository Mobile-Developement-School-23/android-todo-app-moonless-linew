package ru.linew.todoapp.di.module

import dagger.Module
import dagger.Provides
import ru.linew.todoapp.data.datasource.TodoDataSourceImpl
import ru.linew.todoapp.data.repository.datasource.TodoDataSource


@Module
class DataSourceModule {

    @Provides
    fun provideDataSource(): TodoDataSource{
        return TodoDataSourceImpl()
    }

}