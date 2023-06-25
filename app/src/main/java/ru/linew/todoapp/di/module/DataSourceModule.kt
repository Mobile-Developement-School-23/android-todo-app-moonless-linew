package ru.linew.todoapp.di.module

import dagger.Binds
import dagger.Module
import ru.linew.todoapp.data.datasource.local.LocalDataSourceImpl
import ru.linew.todoapp.data.datasource.remote.RemoteDataSourceImpl
import ru.linew.todoapp.data.repository.datasource.local.LocalDataSource
import ru.linew.todoapp.data.repository.datasource.remote.RemoteDataSource


@Module
interface DataSourceModule {

    @Binds
    fun bindRemoteDataSource(todoRemoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

}