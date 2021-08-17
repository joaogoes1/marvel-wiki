package com.joaogoes.marvelwiki.characters.di

import com.joaogoes.marvelwiki.characters.data.datasource.RemoteDataSource
import com.joaogoes.marvelwiki.characters.data.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
interface RemoteDatasourceModule {

    @Binds
    fun bindsRemoteDataSource(
        bindsRemoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource
}