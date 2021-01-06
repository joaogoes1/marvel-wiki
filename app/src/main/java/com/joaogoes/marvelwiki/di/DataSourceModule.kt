package com.joaogoes.marvelwiki.di

import com.joaogoes.marvelwiki.data.datasource.LocalDataSource
import com.joaogoes.marvelwiki.data.datasource.LocalDataSourceImpl
import com.joaogoes.marvelwiki.data.datasource.RemoteDataSource
import com.joaogoes.marvelwiki.data.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    fun bindRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource
}